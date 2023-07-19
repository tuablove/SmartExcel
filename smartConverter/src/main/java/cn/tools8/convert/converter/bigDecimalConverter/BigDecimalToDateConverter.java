package cn.tools8.convert.converter.bigDecimalConverter;

import cn.tools8.convert.IConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BigDecimal To Date
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class BigDecimalToDateConverter extends AbstractBigDecimalConverter implements IConverter {
    @Override
    public Object doConvert(BigDecimal value) {
        return new Date(value.longValue());
    }
}
