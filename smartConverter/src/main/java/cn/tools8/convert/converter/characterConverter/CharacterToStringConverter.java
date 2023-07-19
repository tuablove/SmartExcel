package cn.tools8.convert.converter.characterConverter;

/**
 * Character To String
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToStringConverter extends AbstractCharacterConverter {

    @Override
    public Object doConvert(Character value) {
        return String.valueOf(value);
    }
}
