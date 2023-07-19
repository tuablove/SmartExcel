package cn.tools8.convert.converter.byteConverter;

/**
 * byte To Long
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToLongConverter extends AbstractByteConverter {

    @Override
    public Object doConvert(Byte value) {
        return Long.parseLong(String.valueOf(value));
    }
}
