package cn.tools8.convert.converter.stringConverter;

/**
 * StringToInteger
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToByteArrayConverter extends AbstractStringConverter{

    @Override
    public Object doConvert(String value) {
        return value.getBytes();
    }
}
