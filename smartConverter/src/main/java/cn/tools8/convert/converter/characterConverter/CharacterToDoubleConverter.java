package cn.tools8.convert.converter.characterConverter;

/**
 * Character to double
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToDoubleConverter extends AbstractCharacterConverter {
    @Override
    public Object doConvert(Character value) {
        return Double.parseDouble(String.valueOf(value));
    }
}
