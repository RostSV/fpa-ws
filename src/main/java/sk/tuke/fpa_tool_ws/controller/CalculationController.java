package sk.tuke.fpa_tool_ws.controller;

import org.springframework.web.bind.annotation.*;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.service.CalculationService;

import java.util.Collection;

@RestController
@RequestMapping("/api/calculations")
public class CalculationController {

    private final CalculationService calculationService;

    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping
    public ApiResponse<Collection<CalculationDto>>  getCalculations() {
        Collection<CalculationDto> result = this.calculationService.getCalculations();
        return new ApiResponse<>(200, "Calculations retrieved successfully", result);
    }

    @PostMapping
    public ApiResponse<Object> createCalculation(@RequestBody CalculationDto dto) {
        this.calculationService.createCalculation(dto);
        return new ApiResponse<>(200, "Calculation created successfully", null);
    }

    @GetMapping("/{userId}")
    public ApiResponse<Collection<CalculationDto>> getCalculations(@PathVariable String userId) {
        Collection<CalculationDto> result = this.calculationService.getCalculationsByUserId(userId);
        return new ApiResponse<>(200, "Calculations retrieved successfully", result);
    }
}
