package cn.tools8.convert.converter.characterConverter;

/**
 * Character To Long
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToLongConverter extends AbstractCharacterConverter {

    @Override
    public Object doConvert(Character value) {
        return Long.parseLong(String.valueOf(value));
    }
}
