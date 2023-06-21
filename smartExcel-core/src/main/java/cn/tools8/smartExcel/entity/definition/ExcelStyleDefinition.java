package cn.tools8.smartExcel.entity.definition;

import cn.tools8.smartExcel.handler.IWriteDataCellStyleHandler;

/**
 * 样式定义
 *
 * @author tuaobin 2023/6/21$ 17:16$
 */
public class ExcelStyleDefinition {
    /**
     * 数据格式设置
     */
    private String dataFormat;
    /**
     * 单元格样式设置
     */
    private IWriteDataCellStyleHandler cellStyleHandler;

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public IWriteDataCellStyleHandler getCellStyleHandler() {
        return cellStyleHandler;
    }

    public void setCellStyleHandler(IWriteDataCellStyleHandler cellStyleHandler) {
        this.cellStyleHandler = cellStyleHandler;
    }
}
