package sk.tuke.fpa_tool_ws.service;

import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.CalculationGroupDto;

import java.io.IOException;
import java.util.List;

public interface PdfGeneratorService {

    byte[] generatePdfReport(List<CalculationDto> calculations) throws IOException;

    byte[] generatePdfReport(List<CalculationDto> calculations, CalculationGroupDto group) throws IOException;
}
