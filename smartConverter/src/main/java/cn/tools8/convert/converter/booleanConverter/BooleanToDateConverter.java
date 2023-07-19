package cn.tools8.convert.converter.booleanConverter;

import cn.tools8.convert.IConverter;

import java.util.Date;

/**
 * Long To date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BooleanToDateConverter extends AbstractBooleanConverter implements IConverter {

    @Override
    public Object doConvert(Boolean value) {
        return new Date(value ? 1 : 0);
    }
}
