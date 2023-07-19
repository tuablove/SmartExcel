package cn.tools8.convert.converter.booleanConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To Float
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BooleanToFloatConverter extends AbstractBooleanConverter implements IConverter {

    @Override
    public Object doConvert(Boolean value) {
        return (float) (value ? 1 : 0);
    }
}
