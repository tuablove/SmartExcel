package cn.tools8.convert.converter.IntegerConverter;

import cn.tools8.convert.IConverter;

/**
 * IntegerToString
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class IntegerToDoubleConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return ((Integer) object).doubleValue();
    }
}
