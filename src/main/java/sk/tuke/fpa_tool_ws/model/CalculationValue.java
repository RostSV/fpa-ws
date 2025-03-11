package sk.tuke.fpa_tool_ws.model;

public class CalculationValue {

    private Object value;
    private String name;


    public CalculationValue(Object value, String name) {
        this.value = value;
        this.name = name;
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
}
