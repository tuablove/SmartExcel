package cn.tools8.convert.converter.floatConverter;

import cn.tools8.convert.IConverter;

/**
 * Float To Boolean
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class FloatToBooleanConverter extends AbstractFloatConverter implements IConverter {


    @Override
    public Object doConvert(Float value) {
        return 1F == value;
    }
}
