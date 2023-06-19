package cn.tools8.convert.converter.stringConverter;

/**
 * String To Byte
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToByteConverter extends AbstractStringConverter {

    @Override
    public Object doConvert(String value) {
        return (byte) value.charAt(0);
    }
}
