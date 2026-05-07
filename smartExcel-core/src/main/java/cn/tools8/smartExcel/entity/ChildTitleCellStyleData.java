package cn.tools8.smartExcel.entity;

import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.interfaces.IExcelWriteCellStyleManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 子标题样式数据
 *
 * @author tuaobin
 */
public class ChildTitleCellStyleData {
    private int childIndex;
    private String titleName;
    private int realColumn;
    private Cell cell;
    private CellStyle defaultCellStyle;
    private IExcelWriteCellStyleManager styleManager;
    private IExcelCellStyleCreator styleCreator;

    public ChildTitleCellStyleData(int childIndex, String titleName, int realColumn, Cell cell, CellStyle defaultCellStyle,
                                   IExcelWriteCellStyleManager styleManager, IExcelCellStyleCreator styleCreator) {
        this.childIndex = childIndex;
        this.titleName = titleName;
        this.realColumn = realColumn;
        this.cell = cell;
        this.defaultCellStyle = defaultCellStyle;
        this.styleManager = styleManager;
        this.styleCreator = styleCreator;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public String getTitleName() {
        return titleName;
    }

    public int getRealColumn() {
        return realColumn;
    }

    public Cell getCell() {
        return cell;
    }

    public CellStyle getDefaultCellStyle() {
        return defaultCellStyle;
    }

    public IExcelWriteCellStyleManager getStyleManager() {
        return styleManager;
    }

    public IExcelCellStyleCreator getStyleCreator() {
        return styleCreator;
    }
}
