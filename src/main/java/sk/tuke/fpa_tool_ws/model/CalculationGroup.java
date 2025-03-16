package sk.tuke.fpa_tool_ws.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import sk.tuke.fpa_tool_ws.enums.CalculationGroupType;
import sk.tuke.fpa_tool_ws.model.common.Info;

import java.time.Instant;

@Document
public class CalculationGroup {
    @Id
    private String id;

    @Indexed
    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @Indexed(unique = true)
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private CalculationGroupType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }


    public CalculationGroupType getType() {
        return type;
    }

    public void setType(CalculationGroupType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
