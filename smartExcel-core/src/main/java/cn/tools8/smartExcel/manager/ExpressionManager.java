package cn.tools8.smartExcel.manager;

import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.handler.IWriteTitleExpressionHandler;
import cn.tools8.smartExcel.interfaces.IExpressionCreator;
import org.apache.commons.jexl3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表达式管理
 *
 * @author tuaobin 2023/6/21$ 10:25$
 */
public class ExpressionManager implements IExpressionCreator {
    private static final Logger logger = LoggerFactory.getLogger(ExpressionManager.class);

    private Map<String, Object> objectMap = new HashMap<>();
    private Map<String, JxltEngine.Expression> cacheMap = new HashMap<>();
    private JexlContext context;
    private JxltEngine engine;
    private volatile boolean initialized = false;
    private IWriteTitleExpressionHandler titleExpressionHandler;
    private List<? extends WriteDataBase> dataList;

    public ExpressionManager() {

    }

    public List<? extends WriteDataBase> getDataList() {
        return dataList;
    }

    public void setDataList(List<? extends WriteDataBase> dataList) {
        this.dataList = dataList;
    }

    public void setTitleExpressionHandler(IWriteTitleExpressionHandler titleExpressionHandler) {
        this.titleExpressionHandler = titleExpressionHandler;
    }

    public ExpressionManager(IWriteTitleExpressionHandler titleExpressionHandler) {
        this.titleExpressionHandler = titleExpressionHandler;
    }

    public ExpressionManager(IWriteTitleExpressionHandler titleExpressionHandler, List<? extends WriteDataBase> dataList) {
        this.titleExpressionHandler = titleExpressionHandler;
        this.dataList = dataList;
    }

    /**
     * 初始化
     */
    private void init() {
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    JexlEngine jexl = new JexlBuilder().create();
                    engine = jexl.createJxltEngine();
                    // 创建 JEXL 上下文对象
                    context = new MapContext();
                    if (titleExpressionHandler != null) {
                        titleExpressionHandler.onCreating(dataList, this);
                    }
                    for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                        context.set(entry.getKey(), entry.getValue());
                    }
                    initialized = true;
                }
            }
        }
    }

    /**
     * 是否有表达式
     *
     * @param expression
     * @return
     */
    public boolean hasExpression(String expression) {
        JxltEngine.Expression parseExpression = parseExpression(expression);
        return parseExpression != null;
    }

    /**
     * 解析
     *
     * @param expression
     * @return
     * @throws
     */
    public Object parse(String expression) {
        JxltEngine.Expression engineExpression = parseExpression(expression);
        return engineExpression.evaluate(context);
    }

    /**
     * 解析表达式
     *
     * @param expression
     * @return
     */
    private JxltEngine.Expression parseExpression(String expression) {
        init();
        if (cacheMap.containsKey(expression)) {
            return cacheMap.get(expression);
        } else {
            JxltEngine.Expression engineExpression = null;
            try {
                engineExpression = engine.createExpression(expression);
                cacheMap.put(expression, engineExpression);
            } catch (Exception ignore) {
//                logger.error(String.format("expression:%s", expression), ex);
            }
            return engineExpression;
        }
    }


    @Override
    public Map<String, Object> getExpressions() {
        return objectMap;
    }

    @Override
    public void put(String key, Object value) {
        if (initialized) {
            context.set(key, value);
        } else {
            objectMap.put(key, value);
        }
    }

    public static void main(String[] args) {
        JexlEngine jexl = new JexlBuilder().create();
        JxltEngine jxlt = jexl.createJxltEngine();
        JexlContext context = new MapContext();
        context.set("user", "hello");
        context.set("bar", "world");
        JxltEngine.Expression expr = jxlt.createExpression("Hello ${user}");
        String result = expr.evaluate(context).toString();
//        JexlEngine engine = new JexlBuilder().create();
//        JexlContext context = new MapContext();
//        context.set("foo", "hello");
//        context.set("bar", "world");
//        JexlExpression expression = engine.createExpression("${foo}");
//        Object result = expression.evaluate(context);


//        Map<String, Object> contextObjects = new HashMap<>();
//        contextObjects.put("user", "朋友");
//        contextObjects.put("order", "免费的");
//        Object result = evaluateJEXL("Hello, ${user}! Your order id is ${order}.", contextObjects);
        System.out.println(result);
    }

    public static Object evaluateJEXL(String expr, Map<String, Object> contextObjects) {
        // 创建 JEXL 引擎对象
        JexlEngine engine = new JexlBuilder().create();
        // 创建 JEXL 上下文对象
        JexlContext context = new MapContext();
        // 添加多个对象到上下文中
        for (Map.Entry<String, Object> entry : contextObjects.entrySet()) {
            context.set(entry.getKey(), entry.getValue());
        }
        // 创建表达式对象，并求值
        JexlExpression expression = engine.createExpression(expr);
        return expression.evaluate(context);
    }
}
