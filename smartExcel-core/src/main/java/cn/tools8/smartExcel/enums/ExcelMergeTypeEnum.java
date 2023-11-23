package cn.tools8.smartExcel.enums;

import org.apache.poi.ss.SpreadsheetVersion;

/**
 * excel单元格合并类型
 * @author tuaobin 2023/6/19$ 17:15$
 */
public enum ExcelMergeTypeEnum {
    NONE(0,"不合并"),
    VERTICAL_ALL(1,"都设置了 VERTICAL_ALL 的列竖向自动合并"),
    ;
    private int value;
    private String description;

    ExcelMergeTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
