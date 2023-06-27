package cn.tools8.smartExcel.interfaces;

import java.util.Map;

/**
 * 表达式创建
 * @author tuaobin 2023/6/21$ 10:27$
 */
public interface IExpressionCreator {
    /**
     * 获取所有表达式
     * @return
     */
    Map<String,Object> getExpressions();

    /**
     * 添加表达式
     * @param key
     * @param value
     */
    void put(String key,Object value);
}
