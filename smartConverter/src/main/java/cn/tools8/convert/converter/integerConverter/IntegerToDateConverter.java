package cn.tools8.convert.converter.integerConverter;

import cn.tools8.convert.IConverter;

import java.util.Date;

/**
 * Integer To date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class IntegerToDateConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return new Date((Integer)object);
    }
}
