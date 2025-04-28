package sk.tuke.fpa_tool_ws.dto;

import java.util.Date;

public class CalculationCompareResultDto {
    private String file1Name;
    private String file2Name;
    private double similarity;
    private Date date;

    public CalculationCompareResultDto(String file1Name, String file2Name, double similarity, Date date) {
        this.file1Name = file1Name;
        this.file2Name = file2Name;
        this.similarity = similarity;
        this.date = date;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public String getFile2Name() {
        return file2Name;
    }

    public void setFile2Name(String file2Name) {
        this.file2Name = file2Name;
    }

    public String getFile1Name() {
        return file1Name;
    }

    public void setFile1Name(String file1Name) {
        this.file1Name = file1Name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
