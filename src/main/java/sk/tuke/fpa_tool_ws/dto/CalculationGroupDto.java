package sk.tuke.fpa_tool_ws.dto;


import sk.tuke.fpa_tool_ws.enums.CalculationGroupType;
import sk.tuke.fpa_tool_ws.model.common.Info;

import java.time.Instant;

public class CalculationGroupDto {
    private String id;
    private Instant createdAt;
    private CalculationGroupType type;
    private String name;
    private String description;

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

}
