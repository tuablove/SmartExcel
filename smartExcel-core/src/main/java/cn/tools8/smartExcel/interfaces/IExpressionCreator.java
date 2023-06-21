package cn.tools8.smartExcel.interfaces;

import java.util.Map;

/**
 * @author tuaobin 2023/6/21$ 10:27$
 */
public interface IExpressionCreator {
    Map<String,Object> getExpressions();
    void put(String key,Object value);
}
