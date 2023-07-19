package cn.tools8.convert.converter.characterConverter;

/**
 * Character To Short
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToShortConverter extends AbstractCharacterConverter {

    @Override
    public Object doConvert(Character value) {
        return Short.parseShort(String.valueOf(value));
    }
}
