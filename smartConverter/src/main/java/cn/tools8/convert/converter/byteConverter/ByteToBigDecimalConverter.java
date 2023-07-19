package cn.tools8.convert.converter.byteConverter;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * byte To BigDecimal
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToBigDecimalConverter extends AbstractByteConverter {

    @Override
    public Object doConvert(Byte value) {
        if(NumberUtils.isDigits(value.toString())) {
            return new BigDecimal(value);
        }
        return null;
    }
}
