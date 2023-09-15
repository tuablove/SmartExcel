package cn.tools8.smartExcel.config;

import cn.tools8.smartExcel.enums.ExcelTypeEnum;
import cn.tools8.smartExcel.handler.IWriteGenericCellStyleHandler;
import cn.tools8.smartExcel.handler.IWriteTitleCellStyleHandler;
import cn.tools8.smartExcel.handler.IWriteTitleExpressionHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 写入excel配置
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelWriteTemplateConfig extends ExcelWriteFieldConfig{
    /**
     * 默认2007格式的excel ,false为97格式
     */
    private ExcelTypeEnum excelType = ExcelTypeEnum.EXCEL2007STREAM;
    /**
     * 模板地址
     */
    private String templateFilePath;
    /**
     * 默认sheet名称
     */
    private String defaultSheetName;
    /**
     * 通用样式设置
     */
    private IWriteGenericCellStyleHandler genericCellStyleHandler;

    /**
     * 数据开始的行索引
     */
    private Integer dataBeginRowIndex;



    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

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

    public Integer getDataBeginRowIndex() {
        return dataBeginRowIndex;
    }

    public void setDataBeginRowIndex(Integer dataBeginRowIndex) {
        this.dataBeginRowIndex = dataBeginRowIndex;
    }
}
