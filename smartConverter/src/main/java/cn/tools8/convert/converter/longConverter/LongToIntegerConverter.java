package cn.tools8.convert.converter.longConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To Integer
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class LongToIntegerConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return ((Long)object).intValue();
    }
}
