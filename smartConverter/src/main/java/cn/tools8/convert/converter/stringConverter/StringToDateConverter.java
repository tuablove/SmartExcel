package cn.tools8.convert.converter.stringConverter;

import cn.tools8.utils.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Date;

/**
 * String To Date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class StringToDateConverter extends AbstractStringConverter {

    @Override
    public Object doConvert(String value) {
        if (NumberUtils.isNumber(value)) {
            return new Date(Long.parseLong(value));
        } else {
            return DateUtils.parseDate(value);
        }
    }
}
