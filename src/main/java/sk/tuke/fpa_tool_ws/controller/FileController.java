package sk.tuke.fpa_tool_ws.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.request.SaveXlsRequest;
import sk.tuke.fpa_tool_ws.service.CompareService;
import sk.tuke.fpa_tool_ws.service.ExcelReaderService;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final ExcelReaderService excelReaderService;
    private final CompareService compareService;

    public FileController(ExcelReaderService excelReaderService, CompareService compareService) {
        this.excelReaderService = excelReaderService;
        this.compareService = compareService;
    }

    @PostMapping("/upload-calculations")
    public ApiResponse<Object> saveFile(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("files") MultipartFile[] files) throws IOException {
        this.excelReaderService.saveExcelFiles(new SaveXlsRequest(name, description, files));
        return new ApiResponse<>(200, "File imported successfully", null);
    }

    @PostMapping("/compare-calculations")
    public ApiResponse<Object> compareFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        double result = this.compareService.compareFiles(files);
        return new ApiResponse<>(200, "Files compared successfully", result);
    }
}
