package sk.tuke.fpa_tool_ws.dto;

public class CalculationCompareResultDto {
    private String file1Name;
    private String file2Name;
    private double similarity;

    public CalculationCompareResultDto(String file1Name, String file2Name, double similarity) {
        this.file1Name = file1Name;
        this.file2Name = file2Name;
        this.similarity = similarity;
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
}
