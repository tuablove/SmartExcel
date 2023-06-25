package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.interfaces.IExcelWriteCellStyleManager;

/**
 * 标题单元格样式处理
 * @author tuaobin 2023/6/20$ 17:48$
 */
public interface IWriteTitleCellStyleHandler {
    /**
     * 标题样式创建
     * @param writeCellStyleManager
     * @param creator
     */
    void onCreating(IExcelWriteCellStyleManager writeCellStyleManager, IExcelCellStyleCreator creator);
}
