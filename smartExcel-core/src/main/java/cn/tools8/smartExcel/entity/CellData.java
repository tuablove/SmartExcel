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
    private Cell cell;
    private Object rowData;
    private Object originCellValue;
    private Object cellValue;
    private CellStyle defaultCellStyle;
    private IExcelWriteCellStyleManager styleManager;
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
