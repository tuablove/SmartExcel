package cn.tools8.convert.converter.byteConverter;

import java.nio.ByteBuffer;

/**
 * byte To Integer
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToByteArrayConverter extends AbstractByteConverter {

    @Override
    public Object doConvert(Byte value) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put(value);
        return buffer.array();
    }
}
