package cn.tools8.smartExcel.entity;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 单元格数据
 * @author tuaobin 2023/6/21$ 17:47$
 */
public class CellOriginData {

    /**
     * 单元格
     */
    private Cell cell;
    /**
     * 行数据
     */
    private Object rowData;
    /**
     * 单元格原始数据
     */
    private Object value;
    /**
     * 单元格原始数据类型
     */
    private Class<?> valueType;

    public CellOriginData(Cell cell, Object rowData, Object value, Class<?> valueType) {
        this.cell = cell;
        this.rowData = rowData;
        this.value = value;
        this.valueType = valueType;
    }

    public Cell getCell() {
        return cell;
    }

    public Object getRowData() {
        return rowData;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getValueType() {
        return valueType;
    }
}
