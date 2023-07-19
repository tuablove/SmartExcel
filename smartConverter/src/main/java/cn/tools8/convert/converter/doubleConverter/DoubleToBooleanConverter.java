package cn.tools8.convert.converter.doubleConverter;

import cn.tools8.convert.IConverter;

/**
 * Double To Boolean
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class DoubleToBooleanConverter extends AbstractDoubleConverter implements IConverter {


    @Override
    public Object doConvert(Double value) {
        return 1D == value;
    }
}
