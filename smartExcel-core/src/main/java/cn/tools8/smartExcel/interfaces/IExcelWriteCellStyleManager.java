package cn.tools8.smartExcel.interfaces;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 样式管理
 * @author tuaobin 2023/6/21$ 17:38$
 */
public interface IExcelWriteCellStyleManager extends IExcelWriteCellStyleReader{
    /**
     * 添加一个样式
     * @param type  样式名字
     * @param cellStyle 样式
     */
    void addCellStyle(String type, CellStyle cellStyle);
}
