package cn.tools8.convert.converter.floatConverter;

import cn.tools8.convert.IConverter;

/**
 * Float 转换
 * @author tuaobin 2023/6/19$ 10:16$
 */
public abstract class AbstractFloatConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        if (object == null) {
            return null;
        }
        return doConvert((Float) object);
    }

    /**
     * 转换
     * @param value
     * @return
     */
    public abstract Object doConvert(Float value);
}
