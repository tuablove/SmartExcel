package cn.tools8.convert.converter.doubleConverter;

import cn.tools8.convert.IConverter;

import java.nio.ByteBuffer;

/**
 * Double To ByteArray
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class DoubleToByteArrayConverter extends AbstractDoubleConverter implements IConverter {
    @Override
    public Object doConvert(Double value) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putDouble(value);
        return buffer.array();
    }
}
