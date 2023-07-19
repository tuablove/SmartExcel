package cn.tools8.convert.converter.bigDecimalConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * bigDecimal to boolean
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BigDecimalToBooleanConverter extends AbstractBigDecimalConverter implements IConverter {

    @Override
    public Object doConvert(BigDecimal value) {
        return BigDecimal.ONE.compareTo(value) == 0;
    }
}
