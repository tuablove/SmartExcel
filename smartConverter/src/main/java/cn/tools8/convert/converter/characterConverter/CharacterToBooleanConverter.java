package cn.tools8.convert.converter.characterConverter;

/**
 * Character To Integer
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToBooleanConverter extends AbstractCharacterConverter {

    @Override
    public Object doConvert(Character value) {
        return value.equals('1');
    }
}
