package cn.tools8.convert.converter.longConverter;

import cn.tools8.convert.IConverter;

import java.util.Date;

/**
 * Long To date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class LongToDateConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return new Date((Long)object);
    }
}
