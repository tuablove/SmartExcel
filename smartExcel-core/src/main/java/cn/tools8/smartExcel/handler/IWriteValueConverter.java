package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.entity.CellOriginData;

/**
 * 数值转换
 * @author tuaobin 2023/6/16$ 16:23$
 */
public interface IWriteValueConverter {
    /**
     * 转换
     * @param cellValue 数值
     * @return
     */
    Object convert(CellOriginData cellValue);
}
