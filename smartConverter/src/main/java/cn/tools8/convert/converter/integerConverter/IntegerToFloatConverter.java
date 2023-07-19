package cn.tools8.convert.converter.integerConverter;

import cn.tools8.convert.IConverter;

/**
 * Integer To Float
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class IntegerToFloatConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return ((Integer) object).floatValue();
    }
}
