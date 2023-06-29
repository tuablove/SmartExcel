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
public class ExcelWriteConfig {
    /**
     * 密码
     */
    private String password;
    /**
     * 默认2007格式的excel ,false为97格式
     */
    private ExcelTypeEnum excelType = ExcelTypeEnum.EXCEL2007STREAM;
    /**
     * 模板地址
     */
    private String templateFilePath;
    /**
     * 输出文件地址
     */
    private String filePath;
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
     * 标题表达式
     */
    private IWriteTitleExpressionHandler titleExpressionHandler;
    /**
     * 设置包含的字段
     */
    private Set<String> includeFields;
    /**
     * 设置不包含的字段,不包含字段最后处理
     * 如果包含字段设置了，不包含字段列表也设置了，最终结果未不包含
     */
    private Set<String> excludeFields;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public IWriteTitleCellStyleHandler getTitleCellStyleHandler() {
        return titleCellStyleHandler;
    }

    public void setTitleCellStyleHandler(IWriteTitleCellStyleHandler titleCellStyleHandler) {
        this.titleCellStyleHandler = titleCellStyleHandler;
    }

    public IWriteTitleExpressionHandler getTitleExpressionHandler() {
        return titleExpressionHandler;
    }

    public void setTitleExpressionHandler(IWriteTitleExpressionHandler titleExpressionHandler) {
        this.titleExpressionHandler = titleExpressionHandler;
    }

    public Set<String> getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(Set<String> includeFields) {
        this.includeFields = includeFields;
    }

    public Set<String> getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(Set<String> excludeFields) {
        this.excludeFields = excludeFields;
    }

    public void setExcludeFields(String... excludeFields) {
        this.excludeFields = new HashSet<String>(Arrays.asList(excludeFields));
    }

    public void setIncludeFields(String... includeFields) {
        this.includeFields = new HashSet<String>(Arrays.asList(includeFields));
    }

}
