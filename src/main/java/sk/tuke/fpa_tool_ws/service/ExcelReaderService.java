package sk.tuke.fpa_tool_ws.service;

import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.request.SaveXlsRequest;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface ExcelReaderService {
    void saveExcelFiles(SaveXlsRequest payload) throws IOException;

    List<List<CalculationDto>> readCalculationsFromFiles(MultipartFile[] files) throws IOException;
}
