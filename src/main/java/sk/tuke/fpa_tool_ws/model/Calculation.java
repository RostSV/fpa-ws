package sk.tuke.fpa_tool_ws.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import sk.tuke.fpa_tool_ws.enums.CalculationType;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Document(collection = "calculations")
public class Calculation {

    @Id
    private UUID id;

    @Indexed
    private UUID createdBy;

    @CreatedDate
    private Instant createdAt;

    private String name;

    private String description;

    private CalculationType type;

    private UUID groupId;

    private Collection<CalculationValue> values;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
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

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }
}
