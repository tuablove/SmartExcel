package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.enums.GenericStyleTypeEnum;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 通用样式处理
 * @author tuaobin 2023/6/20$ 17:48$
 */
public interface IWriteGenericCellStyleHandler {
    /**
     * 样式创建
     * @param typeEnum  样式类型
     * @param styleCreator 样式创建器
     * @return
     */
    CellStyle onCreate(GenericStyleTypeEnum typeEnum, IExcelCellStyleCreator styleCreator);
}
