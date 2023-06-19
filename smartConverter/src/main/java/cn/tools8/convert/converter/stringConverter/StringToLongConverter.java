package cn.tools8.convert.converter.stringConverter;

/**
 * String To Long
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToLongConverter extends AbstractStringConverter {

    @Override
    public Object doConvert(String value) {
        return Long.parseLong(value);
    }
}
