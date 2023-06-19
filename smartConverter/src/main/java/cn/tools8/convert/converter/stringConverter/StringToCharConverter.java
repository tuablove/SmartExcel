package cn.tools8.convert.converter.stringConverter;

/**
 * string to char
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToCharConverter extends AbstractStringConverter {

    @Override
    public Object doConvert(String value) {
        return value.charAt(0);
    }
}
