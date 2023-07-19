package cn.tools8.convert.converter.floatConverter;

import cn.tools8.convert.IConverter;

/**
 * Float To char
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class FloatToCharacterConverter extends AbstractFloatConverter implements IConverter {
    @Override
    public Object doConvert(Float value) {
        return (char) value.intValue();
    }
}
