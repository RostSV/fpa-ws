package sk.tuke.fpa_tool_ws.service;

import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.model.Calculation;
import sk.tuke.fpa_tool_ws.model.common.Info;


import java.util.Collection;

public interface CalculationService {
    void createCalculation(CalculationDto dto);

    Collection<CalculationDto> getCalculationsByUserId(String userId);

    Collection<CalculationDto> getCalculations();

    Calculation createGroupCalculation(Info calculationInfo);

    void saveCalculationToGroup(String groupId, CalculationDto calculationDto);

    void createGroupWithCalculations(Info calculationInfo, Collection<CalculationDto> calculations);
}
