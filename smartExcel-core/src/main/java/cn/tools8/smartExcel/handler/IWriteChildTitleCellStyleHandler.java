package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.entity.ChildTitleCellStyleData;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 子标题单元格样式处理
 *
 * @author tuaobin
 */
public interface IWriteChildTitleCellStyleHandler {
    /**
     * 子标题样式创建
     *
     * @param data 子标题样式上下文
     * @return 目标样式，为null时走默认回退逻辑
     */
    CellStyle onCreating(ChildTitleCellStyleData data);
}
