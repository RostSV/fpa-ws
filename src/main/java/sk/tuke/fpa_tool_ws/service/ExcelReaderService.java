package sk.tuke.fpa_tool_ws.service;

import sk.tuke.fpa_tool_ws.dto.request.SaveXlsRequest;

import java.io.IOException;

public interface ExcelReaderService {
    void saveExcelFile(SaveXlsRequest payload) throws IOException;
}
