package cn.tools8.smartExcel.config;

import cn.tools8.smartExcel.entity.ValidateResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取excel配置
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReaderConfig {
    /**
     * 密码
     */
    private String password;
    /**
     * 读取sheet页的配置
     */
    private List<ExcelReaderSheetConfig> sheetConfigs;
    /**
     * 是否验证数据
     */
    private boolean validate = true;
    /**
     * 验证组
     */
    private Class<?>[] validateGroups;
    /**
     * 不验证的bean字段值
     */
    private List<String> validateExcludeFields;

    /**
     * 验证结果列表
     */
    private List<ValidateResult> validateResults = new ArrayList<>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ExcelReaderSheetConfig> getSheetConfigs() {
        return sheetConfigs;
    }

    public void setSheetConfigs(List<ExcelReaderSheetConfig> sheetConfigs) {
        this.sheetConfigs = sheetConfigs;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public List<String> getValidateExcludeFields() {
        return validateExcludeFields;
    }

    public void setValidateExcludeFields(List<String> validateExcludeFields) {
        this.validateExcludeFields = validateExcludeFields;
    }

    public Class<?>[] getValidateGroups() {
        return validateGroups;
    }

    public void setValidateGroups(Class<?>[] validateGroups) {
        this.validateGroups = validateGroups;
    }

    public List<ValidateResult> getValidateResults() {
        return validateResults;
    }

    public void setValidateResults(List<ValidateResult> validateResults) {
        this.validateResults = validateResults;
    }
}
