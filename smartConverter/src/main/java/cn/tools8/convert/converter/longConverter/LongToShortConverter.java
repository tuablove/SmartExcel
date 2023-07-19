package cn.tools8.convert.converter.longConverter;

import cn.tools8.convert.IConverter;

/**
 * Long To Short
 *
 * @author tuaobin 2023/6/16$ 10:20$
 */
public class LongToShortConverter implements IConverter {
    @Override
    public Object convert(Object object) {
        return ((Long) object).shortValue();
    }
}
