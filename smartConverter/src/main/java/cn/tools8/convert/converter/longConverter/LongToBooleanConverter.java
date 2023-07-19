package cn.tools8.convert.converter.longConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To Boolean
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class LongToBooleanConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        Long val = (Long) object;
        return val == 1L;
    }
}
