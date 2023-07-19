package cn.tools8.convert.converter.characterConverter;

import java.nio.ByteBuffer;

/**
 * Character To Byte
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToByteConverter extends AbstractCharacterConverter {

    @Override
    public Object doConvert(Character value) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putChar(value);
        return buffer.array()[0];
    }
}
