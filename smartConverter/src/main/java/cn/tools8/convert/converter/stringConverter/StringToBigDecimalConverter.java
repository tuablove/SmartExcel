package cn.tools8.convert.converter.stringConverter;

import java.math.BigDecimal;

/**
 * StringToBigDecimal
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToBigDecimalConverter extends AbstractStringConverter {

    @Override
    public Object doConvert(String value) {
        return new BigDecimal(value);
    }
}
