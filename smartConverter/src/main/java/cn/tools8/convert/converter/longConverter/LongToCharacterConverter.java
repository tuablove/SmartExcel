package cn.tools8.convert.converter.longConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To char
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class LongToCharacterConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return (char)((Long)object).intValue();
    }
}
