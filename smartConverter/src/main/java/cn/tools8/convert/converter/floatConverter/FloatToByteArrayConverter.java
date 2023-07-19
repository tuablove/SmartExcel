package cn.tools8.convert.converter.floatConverter;

import cn.tools8.convert.IConverter;

import java.nio.ByteBuffer;

/**
 * Float To ByteArray
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class FloatToByteArrayConverter extends AbstractFloatConverter implements IConverter {
    @Override
    public Object doConvert(Float value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(value);
        return buffer.array();
    }
}
