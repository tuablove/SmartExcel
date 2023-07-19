package cn.tools8.convert.converter.booleanConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To Integer
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BooleanToIntegerConverter extends AbstractBooleanConverter implements IConverter {
    @Override
    public Object doConvert(Boolean value) {
        return value ? 1 : 0;
    }
}
