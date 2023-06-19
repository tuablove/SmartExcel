package cn.tools8.convert.converter.IntegerConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * IntegerToString
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class IntegerToBooleanConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        Integer val = (Integer) object;
        return !(val == null || val <= 0);
    }
}
