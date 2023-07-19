package cn.tools8.convert.converter.characterConverter;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * Character To BigDecimal
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToBigDecimalConverter extends AbstractCharacterConverter {

    @Override
    public Object doConvert(Character value) {
        if(NumberUtils.isDigits(value.toString())) {
            return new BigDecimal(value);
        }
        return null;
    }
}
