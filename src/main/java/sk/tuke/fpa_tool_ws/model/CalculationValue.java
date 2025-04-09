package sk.tuke.fpa_tool_ws.model;

import sk.tuke.fpa_tool_ws.enums.CalculationValueType;

public class CalculationValue {

    private Object value;
    private String name;
    private CalculationValueType valueType;


    public CalculationValue(Object value, String name) {
        this.value = value;
        this.name = name;
    }

    public CalculationValue(Object value, String name, CalculationValueType valueType) {
        this.value = value;
        this.name = name;
        this.valueType = valueType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CalculationValueType getValueType() {
        return valueType;
    }

    public void setValueType(CalculationValueType valueType) {
        this.valueType = valueType;
    }
}
