package cn.tools8.smartExcel.builder;

import cn.tools8.smartExcel.handler.IWriteTitleCellStyleHandler;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.manager.ExcelWriteCellStyleManager;

/**
 * 标题样式创建
 *
 * @author tuaobin 2023/6/25$ 10:04$
 */
public class TitleCellStyleCreator {


    /**
     * 初始化
     *
     * @param creator
     * @param styleHandler
     * @return
     */
    public static ExcelWriteCellStyleManager create(IExcelCellStyleCreator creator, IWriteTitleCellStyleHandler styleHandler) {
        ExcelWriteCellStyleManager titleCellStyleManager = new ExcelWriteCellStyleManager();
        if (styleHandler != null) {
            styleHandler.onCreating(titleCellStyleManager, creator);
        }
        return titleCellStyleManager;
    }
}
