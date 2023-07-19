package cn.tools8.convert.converter.booleanConverter;

import cn.tools8.convert.IConverter;

import java.nio.ByteBuffer;

/**
 * Long To ByteArray
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BooleanToByteArrayConverter extends AbstractBooleanConverter implements IConverter {
    @Override
    public Object doConvert(Boolean value) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put((byte) (value ? 1 : 0));
        return buffer.array();
    }
}
