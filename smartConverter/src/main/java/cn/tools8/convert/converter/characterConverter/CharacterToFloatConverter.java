package cn.tools8.convert.converter.characterConverter;

/**
 * Character to float
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToFloatConverter extends AbstractCharacterConverter {
    @Override
    public Object doConvert(Character value) {
        return  Float.parseFloat(String.valueOf(value));
    }
}
