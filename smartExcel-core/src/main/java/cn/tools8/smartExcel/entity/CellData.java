package cn.tools8.smartExcel.entity;

import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.interfaces.IExcelWriteCellStyleReader;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 单元格数据
 * @author tuaobin 2023/6/21$ 17:47$
 */
public class CellData {
    private Cell cell;
    private Object rowData;
    private Object originCellValue;
    private Object cellValue;
    private IExcelWriteCellStyleReader styleReader;
    private IExcelCellStyleCreator styleCreator;

    public CellData() {
    }

    public CellData(Cell cell, Object rowData, Object originCellValue, Object cellValue, IExcelWriteCellStyleReader styleReader, IExcelCellStyleCreator styleCreator) {
        this.cell = cell;
        this.rowData = rowData;
        this.originCellValue = originCellValue;
        this.cellValue = cellValue;
        this.styleReader = styleReader;
        this.styleCreator = styleCreator;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Object getRowData() {
        return rowData;
    }

    public void setRowData(Object rowData) {
        this.rowData = rowData;
    }

    public Object getOriginCellValue() {
        return originCellValue;
    }

    public void setOriginCellValue(Object originCellValue) {
        this.originCellValue = originCellValue;
    }

    public Object getCellValue() {
        return cellValue;
    }

    public void setCellValue(Object cellValue) {
        this.cellValue = cellValue;
    }

    public IExcelWriteCellStyleReader getStyleReader() {
        return styleReader;
    }

    public void setStyleReader(IExcelWriteCellStyleReader styleReader) {
        this.styleReader = styleReader;
    }

    public IExcelCellStyleCreator getStyleCreator() {
        return styleCreator;
    }

    public void setStyleCreator(IExcelCellStyleCreator styleCreator) {
        this.styleCreator = styleCreator;
    }
}
