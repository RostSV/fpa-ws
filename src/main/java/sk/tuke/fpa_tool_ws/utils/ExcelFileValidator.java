package sk.tuke.fpa_tool_ws.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class ExcelFileValidator {
    private static final List<String> EXPECTED_HEADERS = Arrays.asList(
            "sk. rok", "predmet", "c. zadania", "UFP", "VAF", "AFP",
            "LOC - jazyk C", "LOC - OO jazyk", "E", "D", "P",
            "EAF", "E", "D", "P"
    );

    public static void validateExcel(MultipartFile file) throws IOException {

        validateFile(file);

        try(InputStream inputStream = file.getInputStream()){
            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(PropertiesManager.getXlsImportSheet());

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found");
            }
            validateHeaders(sheet);
        }
    }

    private static void validateFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        if (fileName == null || !fileName.endsWith(".xls")) {
            throw new IllegalArgumentException("Invalid file format. Only .xls is supported.");
        }

        if(file.isEmpty() || file.getSize() == 0 || file.getBytes().length == 0) {
            throw new IllegalArgumentException("File is empty");
        }
    }

    private static void validateHeaders(Sheet sheet) {
        Row headerRow = sheet.getRow(PropertiesManager.getXlsHeaderOffset() - 1);
        if (headerRow == null || headerRow.getPhysicalNumberOfCells() < EXPECTED_HEADERS.size()) {
            throw new IllegalArgumentException("Invalid header row");
        }

        for (int i = 0; i < EXPECTED_HEADERS.size(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || cell.getCellType() != CellType.STRING) {
                throw new IllegalArgumentException("Invalid header cell at column " + (i + 1));
            }
            String actualHeader = cell.getStringCellValue().trim();
            String expectedHeader = EXPECTED_HEADERS.get(i).trim();

            if (!actualHeader.equalsIgnoreCase(expectedHeader)) {
                throw new IllegalArgumentException("Invalid header cell at column " + (i + 1));
            }
        }
    }
}
