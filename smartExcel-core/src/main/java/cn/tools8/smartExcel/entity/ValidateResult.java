package cn.tools8.smartExcel.entity;

import java.util.List;
import java.util.Map;

/**
 * 验证结果
 * @author tuaobin 2023/7/7$ 16:11$
 */
public class ValidateResult {
    /**
     * sheet索引
     */
    private Integer sheetIndex;
    //excel行号
    private int row;
    //行数据
    private Object rowData;
    //错误消息
    private Map<String, List<String>> errorMessages;

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Object getRowData() {
        return rowData;
    }

    public void setRowData(Object rowData) {
        this.rowData = rowData;
    }

    public Map<String, List<String>> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, List<String>> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
