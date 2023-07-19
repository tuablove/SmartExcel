package cn.tools8.convert.converter.longConverter;

import cn.tools8.convert.IConverter;

import java.nio.ByteBuffer;

/**
 * Long To ByteArray
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class LongToByteArrayConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        Long val = (Long) object;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(val);
        return buffer.array();
    }
}
