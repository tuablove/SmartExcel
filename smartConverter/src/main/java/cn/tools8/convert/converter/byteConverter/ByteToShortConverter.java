package cn.tools8.convert.converter.byteConverter;

/**
 * byte To Short
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToShortConverter extends AbstractByteConverter {

    @Override
    public Object doConvert(Byte value) {
        return Short.parseShort(String.valueOf(value));
    }
}
