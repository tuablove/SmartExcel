package cn.tools8.convert.converter.byteConverter;

/**
 * byte to double
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToDoubleConverter extends AbstractByteConverter {
    @Override
    public Object doConvert(Byte value) {
        return Double.parseDouble(String.valueOf(value));
    }
}
