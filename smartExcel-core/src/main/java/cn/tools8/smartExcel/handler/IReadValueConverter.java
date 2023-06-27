package cn.tools8.smartExcel.handler;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 数据转换
 * @author tuaobin 2023/6/16$ 16:23$
 */
public interface IReadValueConverter {
    /**
     * 转换
     * @param cell  单元格
     * @param cellValue 数值
     * @param valueType 数据类型
     * @return
     */
    Object convert(Cell cell,Object cellValue,Class<?> valueType);
}
