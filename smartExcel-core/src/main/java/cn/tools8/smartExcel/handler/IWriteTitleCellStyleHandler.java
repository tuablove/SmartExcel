package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.interfaces.IExcelTitleCellStyleCreator;

/**
 * @author tuaobin 2023/6/20$ 17:48$
 */
public interface IWriteTitleCellStyleHandler {

    void onCreating(IExcelTitleCellStyleCreator styleCreator);
}
