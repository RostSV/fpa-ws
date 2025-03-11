package sk.tuke.fpa_tool_ws.dto;

import sk.tuke.fpa_tool_ws.enums.CalculationType;
import sk.tuke.fpa_tool_ws.model.CalculationValue;

import java.time.Instant;
import java.util.Collection;

public class CalculationDto {
    private Instant createdAt;
    private CalculationType type;
    private Collection<CalculationValue> values;

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
}
