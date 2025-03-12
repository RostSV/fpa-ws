package sk.tuke.fpa_tool_ws.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.request.SaveXlsRequest;
import sk.tuke.fpa_tool_ws.mapper.CalculationMapper;
import sk.tuke.fpa_tool_ws.model.CalculationValue;
import sk.tuke.fpa_tool_ws.model.common.Info;
import sk.tuke.fpa_tool_ws.service.CalculationService;
import sk.tuke.fpa_tool_ws.service.ExcelReaderService;

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
    public void saveExcelFile(SaveXlsRequest payload) throws IOException {
        MultipartFile file = payload.getFile();
        validateFile(file);

        Collection<CalculationDto> calculations = new ArrayList<>();
        for(List<CalculationValue> values : readFile(file)){
            calculations.add(CalculationMapper.getEmptyCalculationDtoWithValues(values));
        }

        calculationService.createGroupWithCalculations(new Info(getName(payload), payload.getDescription()), calculations);
    }

    private List<List<CalculationValue>> readFile(MultipartFile file) throws IOException {
        List<String> headers;
        List<List<CalculationValue>> calculationValues = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new HSSFWorkbook(is)) {
            // Read first sheet
            Sheet sheet = workbook.getSheetAt(0);

            if(sheet == null) {
                throw new IllegalArgumentException("Sheet is empty");
            }

            if(sheet.getPhysicalNumberOfRows() <=  2){
                throw new IllegalArgumentException("Sheet is empty");
            }

            headers = getHeaders(sheet);

            for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                List<CalculationValue> values = new ArrayList<>();
                Row row = sheet.getRow(rowIndex);

                if(row == null) {
                    break;
                }

                for (int colIndex = 0; colIndex < headers.size(); colIndex++){
                    values.add(new CalculationValue(getCellValue(row.getCell(colIndex)), headers.get(colIndex)));
                }
                calculationValues.add(values);
            }
        }

        return calculationValues;
    }

    private String getName(SaveXlsRequest payload) {

        if(!payload.getName().isEmpty()){
            return payload.getName() + "( " + payload.getFile().getOriginalFilename() + " )";
        }

        return payload.getFile().getOriginalFilename();
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

    private void validateFile(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        if (fileName == null || !fileName.endsWith(".xls")) {
            throw new IllegalArgumentException("Invalid file format. Only .xls is supported.");
        }

        if(file.isEmpty() || file.getSize() == 0 || file.getBytes().length == 0) {
            throw new IllegalArgumentException("File is empty");
        }
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
}