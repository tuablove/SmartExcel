package cn.tools8.smartExcelDemo.handler;

import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.handler.IWriteTitleExpressionHandler;
import cn.tools8.smartExcel.interfaces.IExpressionCreator;

import java.util.List;

/**
 * @author tuaobin 2023/6/25$ 14:44$
 */
public class TitleExpressionHandler implements IWriteTitleExpressionHandler {

    @Override
    public void onCreating(List<?> dataList, IExpressionCreator creator) {
        creator.put("sno","论文编号");
    }
}
