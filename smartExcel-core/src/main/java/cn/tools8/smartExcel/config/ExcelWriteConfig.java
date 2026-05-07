package cn.tools8.smartExcel.config;

import cn.tools8.smartExcel.enums.ExcelTypeEnum;
import cn.tools8.smartExcel.handler.IWriteChildTitleCellStyleHandler;
import cn.tools8.smartExcel.handler.IWriteDataCellInitializeStyleHandler;
import cn.tools8.smartExcel.handler.IWriteGenericCellStyleHandler;
import cn.tools8.smartExcel.handler.IWriteTitleCellStyleHandler;
import cn.tools8.smartExcel.handler.IWriteTitleExpressionHandler;

/**
 * 写入excel配置
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelWriteConfig extends ExcelWriteFieldConfig {
    /**
     * 默认2007格式的excel ,false为97格式
     */
    private ExcelTypeEnum excelType = ExcelTypeEnum.EXCEL2007STREAM;
    /**
     * 默认sheet名称
     */
    private String defaultSheetName;
    /**
     * 通用样式设置
     */
    private IWriteGenericCellStyleHandler genericCellStyleHandler;

    /**
     * 指定标题格的样式
     */
    private IWriteTitleCellStyleHandler titleCellStyleHandler;
    /**
     * 子标题样式设置
     */
    private IWriteChildTitleCellStyleHandler childTitleCellStyleHandler;
    /**
     * 标题表达式
     */
    private IWriteTitleExpressionHandler titleExpressionHandler;

    /**
     * 单元格初始化样式
     */
    private IWriteDataCellInitializeStyleHandler dataCellInitializeStyleHandler;
    /**
     * 最大子元素数量（如果不设置，将自动遍历获取）
     */
    private Integer maxChildrenCount;

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public String getDefaultSheetName() {
        return defaultSheetName;
    }

    public void setDefaultSheetName(String defaultSheetName) {
        this.defaultSheetName = defaultSheetName;
    }

    public IWriteGenericCellStyleHandler getGenericCellStyleHandler() {
        return genericCellStyleHandler;
    }

    public void setGenericCellStyleHandler(IWriteGenericCellStyleHandler genericCellStyleHandler) {
        this.genericCellStyleHandler = genericCellStyleHandler;
    }

    public IWriteTitleCellStyleHandler getTitleCellStyleHandler() {
        return titleCellStyleHandler;
    }

    public void setTitleCellStyleHandler(IWriteTitleCellStyleHandler titleCellStyleHandler) {
        this.titleCellStyleHandler = titleCellStyleHandler;
    }

    public IWriteChildTitleCellStyleHandler getChildTitleCellStyleHandler() {
        return childTitleCellStyleHandler;
    }

    public void setChildTitleCellStyleHandler(IWriteChildTitleCellStyleHandler childTitleCellStyleHandler) {
        this.childTitleCellStyleHandler = childTitleCellStyleHandler;
    }

    public IWriteTitleExpressionHandler getTitleExpressionHandler() {
        return titleExpressionHandler;
    }

    public void setTitleExpressionHandler(IWriteTitleExpressionHandler titleExpressionHandler) {
        this.titleExpressionHandler = titleExpressionHandler;
    }

    public IWriteDataCellInitializeStyleHandler getDataCellInitializeStyleHandler() {
        return dataCellInitializeStyleHandler;
    }

    public void setDataCellInitializeStyleHandler(IWriteDataCellInitializeStyleHandler dataCellInitializeStyleHandler) {
        this.dataCellInitializeStyleHandler = dataCellInitializeStyleHandler;
    }

    public Integer getMaxChildrenCount() {
        return maxChildrenCount;
    }

    public void setMaxChildrenCount(Integer maxChildrenCount) {
        this.maxChildrenCount = maxChildrenCount;
    }
}
