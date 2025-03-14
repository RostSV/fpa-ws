package sk.tuke.fpa_tool_ws.mapper;

import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.enums.CalculationSourceType;
import sk.tuke.fpa_tool_ws.model.Calculation;
import sk.tuke.fpa_tool_ws.model.CalculationValue;

import java.util.Collection;
import java.util.stream.Collectors;

public class CalculationMapper {

    public static CalculationDto toCalculationDto(Calculation calculation) {
        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setId(calculation.getId());
        calculation.setName(calculation.getName());
        calculation.setDescription(calculation.getDescription());
        calculationDto.setCreatedAt(calculation.getCreatedAt());
        calculationDto.setType(calculation.getType());
        calculationDto.setValues(calculation.getValues());
        calculationDto.setGroupId(calculation.getGroupId());

        return calculationDto;
    }

    public static Calculation toEntity(CalculationDto calculationDto) {
        Calculation calculation = new Calculation();
        calculation.setType(calculationDto.getType());
        calculation.setValues(calculationDto.getValues());
        calculation.setName(calculationDto.getName());
        calculation.setDescription(calculationDto.getDescription());

        CalculationSourceType srcType = calculationDto.getSourceType() == null ? CalculationSourceType.CREATED : calculationDto.getSourceType();

        calculation.setSourceType(srcType);

        return calculation;
    }

    public static CalculationDto getEmptyCalculationDtoWithValues(Collection<CalculationValue> values) {
        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setValues(values);
        return calculationDto;
    }

    public static Collection<CalculationDto> toCalculationDtoCollection(Collection<Calculation> calculations) {
        return calculations.stream().map(CalculationMapper::toCalculationDto).collect(Collectors.toList());
    }

}
