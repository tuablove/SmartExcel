package cn.tools8.convert.converter.integerConverter;

import cn.tools8.convert.IConverter;

/**
 * Integer To char
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class IntegerToCharacterConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return (char)((Integer)object).intValue();
    }
}
