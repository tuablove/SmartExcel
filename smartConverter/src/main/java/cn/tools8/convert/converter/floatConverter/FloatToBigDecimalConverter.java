package cn.tools8.convert.converter.floatConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * Float To BigDecimal
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class FloatToBigDecimalConverter extends AbstractFloatConverter implements IConverter {

    @Override
    public Object doConvert(Float value) {
        return BigDecimal.valueOf(value);
    }
}
