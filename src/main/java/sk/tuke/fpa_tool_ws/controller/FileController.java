package sk.tuke.fpa_tool_ws.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.request.SaveXlsRequest;
import sk.tuke.fpa_tool_ws.enums.FileType;
import sk.tuke.fpa_tool_ws.service.ExcelReaderService;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final ExcelReaderService excelReaderService;

    public FileController(ExcelReaderService excelReaderService) {
        this.excelReaderService = excelReaderService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @GetMapping("/example/{type}")
    public ApiResponse<byte[]> getExample(@PathVariable String type) throws IOException {
        FileType fileType = FileType.valueOf(type.toUpperCase());
        ClassPathResource fileResource = switch (fileType) {
            case XLS -> new ClassPathResource("templates/excel-table-template.xls");
            default -> throw new IllegalArgumentException("Unsupported file type: " + type);
        };

        return new ApiResponse<>(200, "File byte[] data", Files.readAllBytes(fileResource.getFile().toPath()));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @PostMapping("/upload-calculations")
    public ApiResponse<Object> saveFile(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("files") MultipartFile[] files) throws IOException {
        this.excelReaderService.saveExcelFiles(new SaveXlsRequest(name, description, files));
        return new ApiResponse<>(200, "File imported successfully", null);
    }
}
