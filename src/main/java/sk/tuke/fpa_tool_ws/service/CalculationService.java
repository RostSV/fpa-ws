package sk.tuke.fpa_tool_ws.service;

import sk.tuke.fpa_tool_ws.dto.CalculationDto;


import java.util.Collection;

public interface CalculationService {
    void createCalculation(CalculationDto dto);

    Collection<CalculationDto> getCalculationsByUserId(String userId);

    Collection<CalculationDto> getCalculations();
}
