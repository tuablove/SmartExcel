package cn.tools8.convert.converter.booleanConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To Short
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BooleanToShortConverter extends AbstractBooleanConverter implements IConverter {
    @Override
    public Object doConvert(Boolean value) {
        return new Integer(value ? 1 : 0).shortValue();
    }
}
