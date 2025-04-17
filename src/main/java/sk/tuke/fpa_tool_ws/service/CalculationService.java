package sk.tuke.fpa_tool_ws.service;

import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.CalculationGroupDto;
import sk.tuke.fpa_tool_ws.enums.CalculationGroupType;
import sk.tuke.fpa_tool_ws.model.Calculation;
import sk.tuke.fpa_tool_ws.model.CalculationGroup;
import sk.tuke.fpa_tool_ws.model.common.Info;


import java.util.Collection;

public interface CalculationService {
    Collection<CalculationDto> getCalculationsByUserId(String userId);

    Collection<CalculationDto> getCalculations();

    CalculationGroup createGroupCalculation(Info calculationInfo, CalculationGroupType type);

    Collection<CalculationDto> getCalculationsByGroupId(String groupId);

    void createCalculation(CalculationDto dto);

    void saveCalculationToGroup(String groupId, CalculationDto calculationDto);

    void createGroupWithCalculations(Info calculationInfo, CalculationGroupType type, Collection<CalculationDto> calculations);

    void importGroupWithCalculations(Info calculationInfo, Collection<CalculationDto> calculations);

    Collection<CalculationGroupDto> getCalculationsGroups();

    Collection<CalculationDto> getImportedCalculations();

    void updateCalculation(CalculationDto dto);

    void deleteCalculation(String id);

    CalculationGroupDto getCalculationsGroupById(String groupId);

    CalculationDto getCalculationById(String calculationId);
}
