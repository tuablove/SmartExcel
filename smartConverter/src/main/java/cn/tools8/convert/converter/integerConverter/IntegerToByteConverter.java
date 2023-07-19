package cn.tools8.convert.converter.integerConverter;

import cn.tools8.convert.IConverter;

import java.nio.ByteBuffer;

/**
 * Integer To Byte
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class IntegerToByteConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        Integer val = (Integer) object;
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(val);
        return buffer.array()[0];
    }
}
