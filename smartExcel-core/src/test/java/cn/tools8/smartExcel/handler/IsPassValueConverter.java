package cn.tools8.smartExcel.handler;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 是否通过字符串
 *
 * @author tuaobin 2023/6/25$ 13:59$
 */
public class IsPassValueConverter implements IWriteValueConverter {
    @Override
    public Object convert(Cell cell, Object cellValue, Class<?> valueType) {
        if (valueType != null && valueType.isAssignableFrom(Boolean.class)) {
            return (Boolean) cellValue ? "通过" : "不通过";
        }
        return cellValue;
    }
}
