package sk.tuke.fpa_tool_ws.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.CalculationCompareResultDto;
import sk.tuke.fpa_tool_ws.service.CompareService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/compare")
public class CalculationCompareController {

    private final CompareService compareService;

    public CalculationCompareController(CompareService compareService) {
        this.compareService = compareService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @PostMapping("/files")
    public ApiResponse<CalculationCompareResultDto> compareFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        CalculationCompareResultDto result = this.compareService.compareFiles(files);
        return new ApiResponse<>(200, "Files compared successfully", result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @GetMapping("/history")
    public ApiResponse<Collection<CalculationCompareResultDto>> getCompareHistory(){
        return new ApiResponse<>(200, "Compare history retrieved", this.compareService.getCompareHistory());
    }
}
