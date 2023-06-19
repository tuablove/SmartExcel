package cn.tools8.convert.converter.stringConverter;

import cn.tools8.convert.IConverter;

/**
 * String To Short
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToShortConverter extends AbstractStringConverter {

    @Override
    public Object doConvert(String value) {
        return Short.parseShort(value);
    }
}
