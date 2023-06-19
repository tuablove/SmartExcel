package cn.tools8.convert.converter.stringConverter;

import cn.tools8.convert.IConverter;

/**
 * 字符串转换
 * @author tuaobin 2023/6/19$ 10:16$
 */
public abstract class AbstractStringConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        if (object == null || object.equals("")) {
            return null;
        }
        return doConvert((String) object);
    }

    /**
     * 转换
     * @param value
     * @return
     */
    public abstract Object doConvert(String value);
}
