package cn.tools8.smartExcel.interfaces;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 样式读取
 * @author tuaobin 2023/6/21$ 17:38$
 */
public interface IExcelWriteCellStyleReader {
    /**
     * 获取一个样式
     * @param type
     * @return
     */
    CellStyle getCellStyle(String type);
}
