package cn.tools8.convert.converter.stringConverter;

/**
 * string to double
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToDoubleConverter extends AbstractStringConverter {
    @Override
    public Object doConvert(String value) {
        return Double.parseDouble(value);
    }
}
