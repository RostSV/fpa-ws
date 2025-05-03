package sk.tuke.fpa_tool_ws.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import sk.tuke.fpa_tool_ws.enums.CalculationSourceType;
import sk.tuke.fpa_tool_ws.enums.CalculationType;

import java.time.Instant;
import java.util.Collection;

@Document(collection = "calculations")
public class Calculation {

    @Id
    private String id;

    @Indexed
    @CreatedBy
    private String createdBy;

    @Indexed
    @CreatedDate
    private Instant createdAt;

    private String name;

    private String description;

    private CalculationType type;

    private String groupId;

    private CalculationSourceType sourceType;

    private Collection<TableResult> tables;

    public CalculationSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(CalculationSourceType sourceType) {
        this.sourceType = sourceType;
    }

    private Collection<CalculationValue> values;

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
