package sk.tuke.fpa_tool_ws.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PropertiesManager {

    @Value("${xls.header.offset}")
    private String xlsHeaderOffset;

    @Value("${xls.import.sheet}")
    private String xlsImportSheet;

    @Value("${xls.import.info.col.range}")
    private String xlsImportInfoColRange;

    @Value("${xls.import.fpa.col.range}")
    private String xlsImportFpaColRange;

    @Value("${xls.import.b-cocomo.col.range}")
    private String xlsImportBCocomoColRange;

    @Value("${xls.import.i-cocomo.col.range}")
    private String xlsImportICocomoColRange;

    private static PropertiesManager instance;

    @PostConstruct
    private void init() {
        instance = this;
    }

    public static int getXlsHeaderOffset() {
        return Integer.parseInt(instance.xlsHeaderOffset);
    }

    public static int getXlsImportSheet() {
        return Integer.parseInt(instance.xlsImportSheet);
    }

    public static String getXlsImportInfoColRange() {
        return instance.xlsImportInfoColRange;
    }

    public static String getXlsImportFpaColRange() {
        return instance.xlsImportFpaColRange;
    }

    public static String getXlsImportBCocomoColRange() {
        return instance.xlsImportBCocomoColRange;
    }

    public static String getXlsImportICocomoColRange() {
        return instance.xlsImportICocomoColRange;
    }
}