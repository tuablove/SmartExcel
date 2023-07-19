package cn.tools8.convert.converter.bigDecimalConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * 字符串转换
 * @author tuaobin 2023/6/19$ 10:16$
 */
public abstract class AbstractBigDecimalConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        if (object == null) {
            return null;
        }
        return doConvert((BigDecimal) object);
    }

    /**
     * 转换
     * @param value
     * @return
     */
    public abstract Object doConvert(BigDecimal value);
}
