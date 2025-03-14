package sk.tuke.fpa_tool_ws.mapper;

import sk.tuke.fpa_tool_ws.dto.CalculationGroupDto;
import sk.tuke.fpa_tool_ws.model.CalculationGroup;

import java.util.Collection;
import java.util.stream.Collectors;

public class CalculationGroupMapper {

    public static CalculationGroup toEntity(CalculationGroupDto calculationGroupDto) {
        CalculationGroup calculationGroup = new CalculationGroup();
        calculationGroup.setInfo(calculationGroupDto.getInfo());
        calculationGroup.setType(calculationGroupDto.getType());
        return calculationGroup;
    }

    public static CalculationGroupDto toDto(CalculationGroup calculationGroup) {
        CalculationGroupDto calculationGroupDto = new CalculationGroupDto();
        calculationGroupDto.setId(calculationGroup.getId());
        calculationGroupDto.setCreatedAt(calculationGroup.getCreatedAt());
        calculationGroupDto.setInfo(calculationGroup.getInfo());
        calculationGroupDto.setType(calculationGroup.getType());
        return calculationGroupDto;
    }

    public static Collection<CalculationGroupDto> toDto(Collection<CalculationGroup> calculationGroups) {
        return calculationGroups.stream().map(CalculationGroupMapper::toDto).collect(Collectors.toList());
    }
}
