package cn.tools8.convert.converter.doubleConverter;

import cn.tools8.convert.IConverter;

import java.util.Date;

/**
 * Double To date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class DoubleToDateConverter extends AbstractDoubleConverter implements IConverter {

    @Override
    public Object doConvert(Double value) {
        return new Date(value.longValue());
    }
}
