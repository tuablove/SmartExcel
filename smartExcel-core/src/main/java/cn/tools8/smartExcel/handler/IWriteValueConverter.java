package cn.tools8.smartExcel.handler;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @author tuaobin 2023/6/16$ 16:23$
 */
public interface IWriteValueConverter {
    Object convert(Cell cell,Object cellValue,Class<?> valueType);
}
