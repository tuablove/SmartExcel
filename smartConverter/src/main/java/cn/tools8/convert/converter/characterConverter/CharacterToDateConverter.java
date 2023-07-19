package cn.tools8.convert.converter.characterConverter;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Date;

/**
 * Character To Date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class CharacterToDateConverter extends AbstractCharacterConverter {

    @Override
    public Object doConvert(Character value) {
        if (NumberUtils.isDigits(String.valueOf(value))) {
            return new Date(Long.parseLong(String.valueOf(value)));
        }
        return null;
    }
}
