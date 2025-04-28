package sk.tuke.fpa_tool_ws.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompareService {
    double compareFiles(MultipartFile[] files) throws IOException;
}
