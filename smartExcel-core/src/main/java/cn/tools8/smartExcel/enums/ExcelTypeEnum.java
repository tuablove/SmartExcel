package cn.tools8.smartExcel.enums;

import org.apache.poi.ss.SpreadsheetVersion;

/**
 * excel类型
 * @author tuaobin 2023/6/19$ 17:15$
 */
public enum ExcelTypeEnum {
    EXCEL97(1,"EXCEL97",SpreadsheetVersion.EXCEL97),
    EXCEL2007(1,"EXCEL2007",SpreadsheetVersion.EXCEL2007),
    EXCEL2007STREAM(1,"EXCEL2007STREAM",SpreadsheetVersion.EXCEL2007),
    ;
    private int value;
    private String description;
    private SpreadsheetVersion config;

    ExcelTypeEnum(int value, String description, SpreadsheetVersion config) {
        this.value = value;
        this.description = description;
        this.config = config;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public SpreadsheetVersion getConfig() {
        return config;
    }
}
