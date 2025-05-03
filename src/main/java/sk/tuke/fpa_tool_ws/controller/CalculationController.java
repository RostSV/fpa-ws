package sk.tuke.fpa_tool_ws.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.CalculationGroupDto;
import sk.tuke.fpa_tool_ws.service.CalculationService;

import java.util.Collection;

@RestController
@RequestMapping("/api/calculations")
public class CalculationController {

    private final CalculationService calculationService;

    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @GetMapping
    public ApiResponse<Collection<CalculationDto>> getCalculations(@RequestParam (required = false) String count) {
        Collection<CalculationDto> result = this.calculationService.getCalculations(count);
        return new ApiResponse<>(200, "Calculations retrieved successfully", result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @GetMapping("/imported")
    public ApiResponse<Collection<CalculationDto>> getImportedCalculations() {
        Collection<CalculationDto> result = this.calculationService.getImportedCalculations();
        return new ApiResponse<>(200, "Imported calculations retrieved successfully", result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @GetMapping("/{userId}")
    public ApiResponse<Collection<CalculationDto>> getCalculationsByUserId(@PathVariable String userId) {
        Collection<CalculationDto> result = this.calculationService.getCalculationsByUserId(userId);
        return new ApiResponse<>(200, "Calculations retrieved successfully", result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @GetMapping("/groups")
    public ApiResponse<Collection<CalculationGroupDto>> getCalculationsGroups() {
        Collection<CalculationGroupDto> result = this.calculationService.getCalculationsGroups();
        return new ApiResponse<>(200, "Groups retrieved successfully", result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @GetMapping("/groups/info/{groupId}")
    public ApiResponse<CalculationGroupDto> getCalculationsGroupById(@PathVariable String groupId) {
        CalculationGroupDto result = this.calculationService.getCalculationsGroupById(groupId);
        return new ApiResponse<>(200, "Group retrieved successfully", result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @GetMapping("/groups/{groupId}")
    public ApiResponse<Collection<CalculationDto>> getCalculationsByGroupId(@PathVariable String groupId) {
        Collection<CalculationDto> result = this.calculationService.getCalculationsByGroupId(groupId);
        return new ApiResponse<>(200, "Calculations retrieved successfully", result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @PostMapping
    public ApiResponse<Object> createCalculation(@RequestBody CalculationDto dto) {
        this.calculationService.createCalculation(dto);
        return new ApiResponse<>(200, "Calculation created successfully", null);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @PutMapping
    public ApiResponse<Object> updateCalculation(@RequestBody CalculationDto dto) {
        this.calculationService.updateCalculation(dto);
        return new ApiResponse<>(200, "Calculation updated successfully", null);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteCalculation(@PathVariable String id) {
        this.calculationService.deleteCalculation(id);
        return new ApiResponse<>(200, "Calculation deleted successfully", null);
    }

}
