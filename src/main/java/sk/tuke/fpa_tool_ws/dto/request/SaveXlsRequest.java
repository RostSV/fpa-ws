package sk.tuke.fpa_tool_ws.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class SaveXlsRequest {
    private String name;
    private String description;
    private MultipartFile[] files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SaveXlsRequest(String name, String description, MultipartFile[] files) {
        this.name = name;
        this.description = description;
        this.files = files;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
