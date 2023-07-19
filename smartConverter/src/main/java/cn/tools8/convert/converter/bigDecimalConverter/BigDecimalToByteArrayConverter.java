package cn.tools8.convert.converter.bigDecimalConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * BigDecimal To ByteArray
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BigDecimalToByteArrayConverter extends AbstractBigDecimalConverter implements IConverter {

    @Override
    public Object doConvert(BigDecimal value) {
        int scale = value.scale();
        int precision = value.precision();
        byte[] unscaledValue = value.unscaledValue().toByteArray();
        byte[] data = ByteBuffer.allocate(9)
                .order(ByteOrder.BIG_ENDIAN)
                .put((byte) scale)
                .put((byte) precision)
                .put(unscaledValue)
                .array();
        return data;
    }
}
