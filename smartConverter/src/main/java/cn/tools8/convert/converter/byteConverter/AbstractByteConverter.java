package cn.tools8.convert.converter.byteConverter;

import cn.tools8.convert.IConverter;

/**
 * byte 转换
 * @author tuaobin 2023/6/19$ 10:16$
 */
public abstract class AbstractByteConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        if (object == null || object.equals("")) {
            return null;
        }
        return doConvert((Byte) object);
    }

    /**
     * 转换
     * @param value
     * @return
     */
    public abstract Object doConvert(Byte value);
}
