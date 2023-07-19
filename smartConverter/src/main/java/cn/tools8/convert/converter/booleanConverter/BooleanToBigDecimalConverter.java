package cn.tools8.convert.converter.booleanConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * Long To BigDecimal
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BooleanToBigDecimalConverter extends AbstractBooleanConverter implements IConverter {

    @Override
    public Object doConvert(Boolean value) {
        return value ? BigDecimal.ONE : BigDecimal.ZERO;
    }
}
