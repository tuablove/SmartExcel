package cn.tools8.convert.converter.bigDecimalConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * StringToInteger
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BigDecimalToByteArrayConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        BigDecimal num = ((BigDecimal) object);
        int scale = num.scale();
        int precision = num.precision();
        byte[] unscaledValue = num.unscaledValue().toByteArray();
        byte[] data = ByteBuffer.allocate(9)
                .order(ByteOrder.BIG_ENDIAN)
                .put((byte) scale)
                .put((byte) precision)
                .put(unscaledValue)
                .array();
        return data;
    }
}
