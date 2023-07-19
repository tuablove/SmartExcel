package cn.tools8.convert.converter.byteConverter;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Date;

/**
 * byte To Date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class ByteToDateConverter extends AbstractByteConverter {

    @Override
    public Object doConvert(Byte value) {
        if (NumberUtils.isDigits(String.valueOf(value))) {
            return new Date(Long.parseLong(String.valueOf(value)));
        }
        return null;
    }
}
