package sk.tuke.fpa_tool_ws.model;

import java.util.Collection;

public class TableResult {
    private Double result;
    private String tableName;
    private Collection<?> tableValues;

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Collection<?> getTableValues() {
        return tableValues;
    }

    public void setTableValues(Collection<?> tableValues) {
        this.tableValues = tableValues;
    }
}
