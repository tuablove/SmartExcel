package cn.tools8.convert.converter.stringConverter;

/**
 * string to integer
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToIntegerConverter extends AbstractStringConverter{

    @Override
    public Object doConvert(String value) {
        return  Integer.parseInt(value);
    }
}
