package cn.tools8.convert.converter.doubleConverter;

import cn.tools8.convert.IConverter;

/**
 * Double To String
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class DoubleToStringConverter extends AbstractDoubleConverter implements IConverter {
    @Override
    public Object doConvert(Double value) {
        return value.toString();
    }

}
