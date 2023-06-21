package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.interfaces.IExcelTitleCellStyleCreator;
import cn.tools8.smartExcel.interfaces.IExpressionCreator;

/**
 * @author tuaobin 2023/6/20$ 17:48$
 */
public interface IWriteTitleExpressionHandler {

    void onCreating(IExpressionCreator creator);
}
