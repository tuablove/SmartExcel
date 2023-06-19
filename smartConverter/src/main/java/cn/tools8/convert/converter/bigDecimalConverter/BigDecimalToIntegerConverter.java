package cn.tools8.convert.converter.bigDecimalConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * StringToInteger
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BigDecimalToIntegerConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return ((BigDecimal) object).intValue();
    }
}
