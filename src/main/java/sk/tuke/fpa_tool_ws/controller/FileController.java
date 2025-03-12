package sk.tuke.fpa_tool_ws.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.request.SaveXlsRequest;
import sk.tuke.fpa_tool_ws.service.ExcelReaderService;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final ExcelReaderService excelReaderService;

    public FileController(ExcelReaderService excelReaderService) {
        this.excelReaderService = excelReaderService;
    }

    @PostMapping
    public ApiResponse<Object> saveFile(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        this.excelReaderService.saveExcelFile(new SaveXlsRequest(name, description, file));
        return new ApiResponse<>(200, "File imported successfully", null);
    }
}
