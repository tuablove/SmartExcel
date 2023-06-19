package cn.tools8.convert.converter.stringConverter;

/**
 * string to float
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToFloatConverter extends AbstractStringConverter {
    @Override
    public Object doConvert(String value) {
        return  Float.parseFloat(value);
    }
}
