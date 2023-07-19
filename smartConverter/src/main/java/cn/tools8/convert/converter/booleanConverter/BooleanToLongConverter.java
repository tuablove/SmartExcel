package cn.tools8.convert.converter.booleanConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To Long
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BooleanToLongConverter extends AbstractBooleanConverter implements IConverter {

    @Override
    public Object doConvert(Boolean value) {
        return value ? 1L : 0L;
    }
}
