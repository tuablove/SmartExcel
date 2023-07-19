package cn.tools8.convert.converter.longConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * Long To BigDecimal
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class LongToBigDecimalConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return new BigDecimal((Long) object);
    }
}
