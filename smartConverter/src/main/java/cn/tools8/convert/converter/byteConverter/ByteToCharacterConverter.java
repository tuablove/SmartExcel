package cn.tools8.convert.converter.byteConverter;

/**
 * byte to char
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToCharacterConverter extends AbstractByteConverter {

    @Override
    public Object doConvert(Byte value) {
        return (char)(int)value;
    }
}
