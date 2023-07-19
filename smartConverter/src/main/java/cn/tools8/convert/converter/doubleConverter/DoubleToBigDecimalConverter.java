package cn.tools8.convert.converter.doubleConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * Double To BigDecimal
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class DoubleToBigDecimalConverter extends AbstractDoubleConverter implements IConverter {

    @Override
    public Object doConvert(Double value) {
        return BigDecimal.valueOf(value);
    }
}
