package cn.tools8.smartExcel.entity;

import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.interfaces.IExcelWriteCellStyleManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 单元格数据
 * @author tuaobin 2023/6/21$ 17:47$
 */
public class CellData {
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
    private Object originCellValue;
    /**
     * 单元格转换后数据
     */
    private Object cellValue;
    /**
     * 默认样式
     */
    private CellStyle defaultCellStyle;
    /**
     * 样式管理
     */
    private IExcelWriteCellStyleManager styleManager;
    /**
     * 样式创建
     */
    private IExcelCellStyleCreator styleCreator;

    public CellData() {
    }

    public CellData(Cell cell, Object rowData, Object originCellValue, Object cellValue, CellStyle defaultCellStyle, IExcelWriteCellStyleManager styleManager, IExcelCellStyleCreator styleCreator) {
        this.cell = cell;
        this.rowData = rowData;
        this.originCellValue = originCellValue;
        this.cellValue = cellValue;
        this.defaultCellStyle = defaultCellStyle;
        this.styleManager = styleManager;
        this.styleCreator = styleCreator;
    }

    public CellStyle getDefaultCellStyle() {
        return defaultCellStyle;
    }

    public Cell getCell() {
        return cell;
    }

    public Object getRowData() {
        return rowData;
    }

    public Object getOriginCellValue() {
        return originCellValue;
    }

    public Object getCellValue() {
        return cellValue;
    }

    public IExcelWriteCellStyleManager getStyleManager() {
        return styleManager;
    }

    public IExcelCellStyleCreator getStyleCreator() {
        return styleCreator;
    }
}
