package cn.tools8.convert.converter.byteConverter;

/**
 * byte to float
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToFloatConverter extends AbstractByteConverter {
    @Override
    public Object doConvert(Byte value) {
        return  Float.parseFloat(String.valueOf(value));
    }
}
