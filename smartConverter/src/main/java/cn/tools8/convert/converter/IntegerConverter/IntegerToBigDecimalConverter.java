package cn.tools8.convert.converter.IntegerConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * IntegerToString
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class IntegerToBigDecimalConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return new BigDecimal((Integer) object);
    }
}
