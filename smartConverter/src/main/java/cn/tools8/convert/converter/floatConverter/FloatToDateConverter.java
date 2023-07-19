package cn.tools8.convert.converter.floatConverter;

import cn.tools8.convert.IConverter;

import java.util.Date;

/**
 * Float To date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class FloatToDateConverter extends AbstractFloatConverter implements IConverter {

    @Override
    public Object doConvert(Float value) {
        return new Date(value.longValue());
    }
}
