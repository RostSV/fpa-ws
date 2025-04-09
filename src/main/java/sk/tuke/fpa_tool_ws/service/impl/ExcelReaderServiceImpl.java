package sk.tuke.fpa_tool_ws.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.request.SaveXlsRequest;
import sk.tuke.fpa_tool_ws.enums.CalculationType;
import sk.tuke.fpa_tool_ws.enums.CalculationValueType;
import sk.tuke.fpa_tool_ws.mapper.CalculationMapper;
import sk.tuke.fpa_tool_ws.model.Calculation;
import sk.tuke.fpa_tool_ws.model.CalculationValue;
import sk.tuke.fpa_tool_ws.model.common.Info;
import sk.tuke.fpa_tool_ws.service.CalculationService;
import sk.tuke.fpa_tool_ws.service.ExcelReaderService;
import sk.tuke.fpa_tool_ws.utils.ExcelFileValidator;
import sk.tuke.fpa_tool_ws.utils.PropertiesManager;
import sk.tuke.fpa_tool_ws.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ExcelReaderServiceImpl implements ExcelReaderService {


    private final CalculationService calculationService;

    public ExcelReaderServiceImpl(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @Override
    public void saveExcelFiles(SaveXlsRequest payload) throws IOException {
        for(MultipartFile file: payload.getFiles()){

            ExcelFileValidator.validateExcel(file);

            Collection<CalculationDto> calculations = new ArrayList<>();
            for(List<CalculationValue> values : readFile(file)){
                calculations.add(CalculationMapper.getEmptyCalculationDtoWithValues(values));
            }

            if(payload.getFiles().length > 1){
                calculationService.importGroupWithCalculations(new Info(file.getOriginalFilename(), null), calculations);
            }else{
                calculationService.importGroupWithCalculations(new Info(getName(payload), payload.getDescription()), calculations);

            }
        }
    }

    private List<List<CalculationValue>> readFile(MultipartFile file) throws IOException {
        List<String> headers;
        List<List<CalculationValue>> calculationValues = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new HSSFWorkbook(is)) {
            // Read first sheet
            Sheet sheet = workbook.getSheetAt(PropertiesManager.getXlsImportSheet());
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            if(sheet == null) {
                throw new IllegalArgumentException("Sheet is empty");
            }

            if(sheet.getPhysicalNumberOfRows() <= PropertiesManager.getXlsHeaderOffset()){
                throw new IllegalArgumentException("Sheet is empty");
            }

            headers = getHeaders(sheet);

            for (int rowIndex = PropertiesManager.getXlsHeaderOffset(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                List<CalculationValue> values = new ArrayList<>();
                Row row = sheet.getRow(rowIndex);

                if(row == null) {
                    break;
                }

                for (int colIndex = 0; colIndex < headers.size(); colIndex++){

                    Cell cell = row.getCell(colIndex);

                    String value = cell != null && cell.getCellType() == CellType.FORMULA
                            ? getCellValueWithFormula(cell, evaluator)
                            : getCellValue(cell);

                    values.add(new CalculationValue(value, headers.get(colIndex), getCalculationValueType(colIndex)));
                }
                calculationValues.add(values);
            }
        }

        return calculationValues;
    }

    private CalculationValueType getCalculationValueType(int colIndex) {
        if (Utils.isInRange(PropertiesManager.getXlsImportFpaColRange(), colIndex)) return CalculationValueType.FP;
        if (Utils.isInRange(PropertiesManager.getXlsImportInfoColRange(), colIndex)) return CalculationValueType.INFO;
        if (Utils.isInRange(PropertiesManager.getXlsImportBCocomoColRange(), colIndex)) return CalculationValueType.B_COCOMO;
        if (Utils.isInRange(PropertiesManager.getXlsImportICocomoColRange(), colIndex)) return CalculationValueType.INT_COCOMO;
        return CalculationValueType.NONE;
    }

    private String getName(SaveXlsRequest payload) {

        if(!payload.getName().isEmpty()){
            return payload.getName() + "(" + payload.getFiles()[0].getOriginalFilename() + ")";
        }

        return payload.getFiles()[0].getOriginalFilename();
    }

    private List<String> getHeaders(Sheet sheet) {
        List<String> headers = new ArrayList<>();
        Row headerRow = sheet.getRow(1);

        if(headerRow == null) {
            throw new IllegalArgumentException("Header row is empty");
        }

        for (Cell cell : headerRow) {
            headers.add(getCellValue(cell));
        }
        return headers;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    private String getCellValueWithFormula(Cell cell, FormulaEvaluator evaluator) {
        if (cell == null) return "";
        CellValue cellValue = evaluator.evaluate(cell);
        return switch (cellValue.getCellType()) {
            case STRING -> cellValue.getStringValue();
            case NUMERIC -> String.valueOf(cellValue.getNumberValue());
            case BOOLEAN -> String.valueOf(cellValue.getBooleanValue());
            default -> "";
        };
    }
}