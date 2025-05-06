package sk.tuke.fpa_tool_ws.service;

import org.springframework.web.multipart.MultipartFile;
import sk.tuke.fpa_tool_ws.dto.CalculationCompareResultDto;

import java.io.IOException;
import java.util.Collection;

public interface CompareService {
    CalculationCompareResultDto compareFiles(MultipartFile[] files) throws IOException;

    Collection<CalculationCompareResultDto> getCompareHistory();
}
