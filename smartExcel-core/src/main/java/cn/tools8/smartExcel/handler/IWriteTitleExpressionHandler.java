package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.interfaces.IExpressionCreator;

import java.util.List;

/**
 * 表头内容表达式
 * @author tuaobin 2023/6/20$ 17:48$
 */
public interface IWriteTitleExpressionHandler {
    /**
     * 表达式创建
     * @param creator
     */
    void onCreating(List<? extends WriteDataBase> dataList,IExpressionCreator creator);
}
