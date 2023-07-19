package cn.tools8.convert.converter.booleanConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To Double
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BooleanToDoubleConverter extends AbstractBooleanConverter implements IConverter {
    @Override
    public Object doConvert(Boolean value) {
        return (double) (value ? 1 : 0);
    }
}
