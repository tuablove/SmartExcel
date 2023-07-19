package cn.tools8.convert.converter.bigDecimalConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;

/**
 * BigDecimal To Char
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BigDecimalToCharacterConverter extends AbstractBigDecimalConverter implements IConverter {
    @Override
    public Object doConvert(BigDecimal value) {
        return value.toPlainString().charAt(0);
    }
}
