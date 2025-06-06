package sk.tuke.fpa_tool_ws.dto;

import sk.tuke.fpa_tool_ws.enums.CalculationSourceType;
import sk.tuke.fpa_tool_ws.enums.CalculationType;
import sk.tuke.fpa_tool_ws.model.CalculationValue;
import sk.tuke.fpa_tool_ws.model.TableResult;

import java.time.Instant;
import java.util.Collection;

public class CalculationDto {

    private String id;
    private String name;
    private String description;
    private String groupId;
    private Instant createdAt;
    private CalculationType type;
    private CalculationSourceType sourceType;
    private Collection<CalculationValue> values;
    private Collection<TableResult> tables;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public CalculationType getType() {
        return type;
    }

    public void setType(CalculationType type) {
        this.type = type;
    }

    public Collection<CalculationValue> getValues() {
        return values;
    }

    public void setValues(Collection<CalculationValue> values) {
        this.values = values;
    }

    public CalculationSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(CalculationSourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Collection<TableResult> getTables() {
        return tables;
    }

    public void setTables(Collection<TableResult> tables) {
        this.tables = tables;
    }
}
