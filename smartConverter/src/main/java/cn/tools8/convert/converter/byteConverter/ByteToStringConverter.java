package cn.tools8.convert.converter.byteConverter;

/**
 * byte To String
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToStringConverter extends AbstractByteConverter {

    @Override
    public Object doConvert(Byte value) {
        return String.valueOf(value);
    }
}
