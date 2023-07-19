package cn.tools8.convert.converter.byteConverter;

/**
 * byte to integer
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToIntegerConverter extends AbstractByteConverter {

    @Override
    public Object doConvert(Byte value) {
        return  Integer.parseInt(String.valueOf(value));
    }
}
