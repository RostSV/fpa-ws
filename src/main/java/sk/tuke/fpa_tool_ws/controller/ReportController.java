package sk.tuke.fpa_tool_ws.controller;

import org.springframework.web.bind.annotation.*;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.CalculationGroupDto;
import sk.tuke.fpa_tool_ws.model.CalculationGroup;
import sk.tuke.fpa_tool_ws.service.CalculationService;
import sk.tuke.fpa_tool_ws.service.PdfGeneratorService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final PdfGeneratorService pdfGeneratorService;
    private final CalculationService calculationService;

    public ReportController(PdfGeneratorService pdfGeneratorService, CalculationService calculationService) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.calculationService = calculationService;
    }

    @PostMapping("/generate-report/calculation/{calculationId}/pdf")
    public ApiResponse<byte[]> generatePdfReport(@PathVariable String calculationId) throws IOException {
        CalculationDto calculation = this.calculationService.getCalculationById(calculationId);

        if(calculation == null) {
            return new ApiResponse<>(404, "Calculation not found", null);
        }
        byte[] result = this.pdfGeneratorService.generatePdfReport(List.of(calculation));
        return new ApiResponse<>(200, "Pdf successfully generated", result);
    }

    @PostMapping("/generate-report/group/{groupId}/pdf")
    public ApiResponse<byte[]> generatePdfReportGroup(@PathVariable String groupId) throws IOException {
        Collection<CalculationDto> calculation = this.calculationService.getCalculationsByGroupId(groupId);
        CalculationGroupDto group = this.calculationService.getCalculationsGroupById(groupId);

        if(calculation == null) {
            return new ApiResponse<>(404, "Group not found", null);
        }
        byte[] result = this.pdfGeneratorService.generatePdfReport(calculation.stream().toList(), group);
        return new ApiResponse<>(200, "Pdf successfully generated", result);
    }
}
