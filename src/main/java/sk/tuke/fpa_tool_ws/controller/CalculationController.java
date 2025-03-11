package sk.tuke.fpa_tool_ws.controller;

import org.springframework.web.bind.annotation.*;
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
    public Collection<CalculationDto> getCalculations() {
        return this.calculationService.getCalculations();
    }

    @PostMapping
    public void createCalculation(@RequestBody CalculationDto dto) {
        this.calculationService.createCalculation(dto);
    }

    @GetMapping("/{userId}")
    public Collection<CalculationDto> getCalculations(@PathVariable String userId) {
        return this.calculationService.getCalculationsByUserId(userId);
    }


}
