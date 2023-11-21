package cn.tools8.smartExcelDemo.handler;

import cn.tools8.smartExcel.entity.CellOriginData;
import cn.tools8.smartExcel.handler.IWriteValueConverter;

/**
 * 是否通过字符串
 *
 * @author tuaobin 2023/6/25$ 13:59$
 */
public class IsPassValueConverter implements IWriteValueConverter {


    @Override
    public Object convert(CellOriginData cellValue) {
        if (cellValue.getValueType() != null && cellValue.getValueType().isAssignableFrom(Boolean.class)) {
            return (Boolean) cellValue.getValue() ? "通过" : "不通过";
        }
        return cellValue;
    }
}
