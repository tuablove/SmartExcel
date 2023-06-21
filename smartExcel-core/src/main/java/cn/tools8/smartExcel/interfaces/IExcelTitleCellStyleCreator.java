package cn.tools8.smartExcel.interfaces;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 * 标题样式创建器
 * @author tuaobin 2023/6/20$ 18:10$
 */
public interface IExcelTitleCellStyleCreator extends IExcelCellStyleCreator{
    /**
     * 添加一个标题的样式
     * @param titleName 原标题
     * @param cellStyle 对应的样式
     */
    void addTitleCellStyle(String titleName,CellStyle cellStyle);
}
