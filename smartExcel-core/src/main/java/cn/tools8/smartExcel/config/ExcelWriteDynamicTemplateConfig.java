package cn.tools8.smartExcel.config;

import cn.tools8.smartExcel.handler.IWriteDataCellInitializeStyleHandler;

/**
 * 写入excel配置
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelWriteDynamicTemplateConfig extends ExcelWriteTemplateConfig {

    /**
     * 标题对应的行索引
     */
    private Integer titleRowIndex = 0;

    /**
     * 单元格初始化样式
     */
    private IWriteDataCellInitializeStyleHandler dataCellInitializeStyleHandler;

    public Integer getTitleRowIndex() {
        return titleRowIndex;
    }

    public void setTitleRowIndex(Integer titleRowIndex) {
        this.titleRowIndex = titleRowIndex;
    }

    public IWriteDataCellInitializeStyleHandler getDataCellInitializeStyleHandler() {
        return dataCellInitializeStyleHandler;
    }

    public void setDataCellInitializeStyleHandler(IWriteDataCellInitializeStyleHandler dataCellInitializeStyleHandler) {
        this.dataCellInitializeStyleHandler = dataCellInitializeStyleHandler;
    }
}
