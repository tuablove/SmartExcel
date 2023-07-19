package cn.tools8.convert.converter.floatConverter;

import cn.tools8.convert.IConverter;

/**
 * Float To Double
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class FloatToDoubleConverter extends AbstractFloatConverter implements IConverter {
    @Override
    public Object doConvert(Float value) {
        return (double)value;
    }
}
