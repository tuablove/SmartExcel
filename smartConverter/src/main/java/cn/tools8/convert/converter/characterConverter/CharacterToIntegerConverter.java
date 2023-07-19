package cn.tools8.convert.converter.characterConverter;

/**
 * Character to integer
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToIntegerConverter extends AbstractCharacterConverter {

    @Override
    public Object doConvert(Character value) {
        return  Integer.parseInt(String.valueOf(value));
    }
}
