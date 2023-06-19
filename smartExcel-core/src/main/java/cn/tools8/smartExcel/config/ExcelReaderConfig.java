package cn.tools8.smartExcel.config;

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
}
