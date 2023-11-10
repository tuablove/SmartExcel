package cn.tools8.smartExcel.utils;

import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.config.ExcelWriteTemplateConfig;

import java.util.Objects;

/**
 * 写入excel配置帮助类
 * @author tuaobin 2023/6/19$ 13:58$
 */
public class ExcelWriteConfigUtils {
    /**
     * 验证配置
     *
     * @param config
     * @return
     */
    public static void validateConfig(ExcelWriteConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("ExcelWriteConfig is null");
        }
        if(Objects.isNull(config.getDefaultSheetName())){
            config.setDefaultSheetName("Sheet1");
        }
        if(Objects.isNull(config.getFilePath())){
            throw new IllegalArgumentException("out put filePath is empty");
        }
        if(Objects.isNull(config.getExcelType())){
            throw new IllegalArgumentException("excelType is empty");
        }
    }

    /**
     * 验证配置
     *
     * @param config
     * @return
     */
    public static void validateConfig(ExcelWriteTemplateConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("ExcelWriteConfig is null");
        }
        if(Objects.isNull(config.getFilePath())){
            throw new IllegalArgumentException("out put filePath is empty");
        }
        if(Objects.isNull(config.getExcelType())){
            throw new IllegalArgumentException("excelType is empty");
        }
    }

}
