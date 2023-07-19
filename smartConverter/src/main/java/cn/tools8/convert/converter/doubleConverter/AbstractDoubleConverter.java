package cn.tools8.convert.converter.doubleConverter;

import cn.tools8.convert.IConverter;

/**
 * double转换
 * @author tuaobin 2023/6/19$ 10:16$
 */
public abstract class AbstractDoubleConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        if (object == null) {
            return null;
        }
        return doConvert((Double) object);
    }

    /**
     * 转换
     * @param value
     * @return
     */
    public abstract Object doConvert(Double value);
}
