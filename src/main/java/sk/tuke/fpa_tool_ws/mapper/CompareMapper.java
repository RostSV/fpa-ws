package sk.tuke.fpa_tool_ws.mapper;

import sk.tuke.fpa_tool_ws.dto.CalculationCompareResultDto;
import sk.tuke.fpa_tool_ws.model.CalculationCompareResult;

import java.util.Date;

public class CompareMapper {
    public static CalculationCompareResultDto toDto(CalculationCompareResult compareResult) {
        return new CalculationCompareResultDto(
                compareResult.getFile1Name(),
                compareResult.getFile2Name(),
                compareResult.getSimilarity(),
                Date.from(compareResult.getCreatedAt()));
    }
}
