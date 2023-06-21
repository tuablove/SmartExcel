package cn.tools8.smartExcel.interfaces;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;

/**
 * 样式创建器
 * @author tuaobin 2023/6/20$ 18:10$
 */
public interface IExcelCellStyleCreator {
    /**
     * 创建一个新样式
     * @return
     */
    CellStyle newCellStyle();

    /**
     * 创建一个新Font
     * @return
     */
    Font newCellFont();

    /**
     * 创建一个新的数据格式
     * @return
     */
    DataFormat newDataFormat();
}
