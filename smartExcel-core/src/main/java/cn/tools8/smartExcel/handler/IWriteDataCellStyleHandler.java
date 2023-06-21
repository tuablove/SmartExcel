package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.entity.CellData;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 数据样式
 * @author tuaobin 2023/6/20$ 17:48$
 */
public interface IWriteDataCellStyleHandler {
    /**
     * 创建样式
     * @param cellData
     */
    CellStyle onCreating(CellData cellData);
}
