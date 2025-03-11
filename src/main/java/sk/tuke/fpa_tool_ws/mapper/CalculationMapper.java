package sk.tuke.fpa_tool_ws.mapper;

import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.model.Calculation;

import java.util.Collection;
import java.util.stream.Collectors;

public class CalculationMapper {

    public static CalculationDto toCalculationDto(Calculation calculation) {
        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setCreatedAt(calculation.getCreatedAt());
        calculationDto.setType(calculation.getType());
        calculationDto.setValues(calculation.getValues());

        return calculationDto;
    }

    public static Calculation toEntity(CalculationDto calculationDto) {
        Calculation calculation = new Calculation();
        calculation.setType(calculationDto.getType());
        calculation.setValues(calculationDto.getValues());

        return calculation;
    }

    public static Collection<CalculationDto> toCalculationDtoCollection(Collection<Calculation> calculations) {
        return calculations.stream().map(CalculationMapper::toCalculationDto).collect(Collectors.toList());
    }
}
