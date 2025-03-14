package sk.tuke.fpa_tool_ws.dto;


import sk.tuke.fpa_tool_ws.enums.CalculationGroupType;
import sk.tuke.fpa_tool_ws.model.common.Info;

import java.time.Instant;

public class CalculationGroupDto {
    private String id;
    private Instant createdAt;
    private Info info;
    private CalculationGroupType type;

    public CalculationGroupType getType() {
        return type;
    }

    public void setType(CalculationGroupType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
