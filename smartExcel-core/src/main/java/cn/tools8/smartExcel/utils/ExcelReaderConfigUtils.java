package cn.tools8.smartExcel.utils;

import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.config.ExcelReaderSheetConfig;
import cn.tools8.smartExcel.config.ExcelReaderWriteConfig;
import cn.tools8.smartExcel.exception.SheetNameNotFoundError;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

/**
 * 读取excel配置帮助类
 * @author tuaobin 2023/6/19$ 13:58$
 */
public class ExcelReaderConfigUtils {
    /**
     * 验证读取配置
     *
     * @param config
     * @return
     */
    public static ExcelReaderConfig validateConfig(ExcelReaderConfig config) {
        if (config == null) {
            config = new ExcelReaderConfig();
        }
        if (Objects.isNull(config.getSheetConfigs()) || config.getSheetConfigs().size() == 0) {
            config.setSheetConfigs(new ArrayList<>());
            config.getSheetConfigs().add(new ExcelReaderSheetConfig(0, 0, null, 0, 1));
        } else {
            for (ExcelReaderSheetConfig sheetConfig : config.getSheetConfigs()) {
                if (sheetConfig.getSheetIndexBegin() == null || sheetConfig.getSheetIndexBegin() < 0) {
                    throw new IllegalArgumentException("sheet config sheetIndexBegin is null");
                }
                if(sheetConfig.getDataBeginRowIndex()==null ||  sheetConfig.getDataBeginRowIndex() < 0){
                    throw new IllegalArgumentException("sheet config dataBeginRowIndex is null");
                }
            }
        }
        return config;
    }
    /**
     * 验证读取配置
     *
     * @param config
     * @return
     */
    public static ExcelReaderWriteConfig validateConfig(ExcelReaderWriteConfig config) {
        if (config == null) {
            config = new ExcelReaderWriteConfig();
        }
        if (Objects.isNull(config.getSheetConfigs()) || config.getSheetConfigs().size() == 0) {
            config.setSheetConfigs(new ArrayList<>());
            config.getSheetConfigs().add(new ExcelReaderSheetConfig(0, 0, null, 0, 1));
        } else {
            for (ExcelReaderSheetConfig sheetConfig : config.getSheetConfigs()) {
                if (sheetConfig.getSheetIndexBegin() == null || sheetConfig.getSheetIndexBegin() < 0) {
                    throw new IllegalArgumentException("sheet config sheetIndexBegin is null");
                }
                if(sheetConfig.getDataBeginRowIndex()==null ||  sheetConfig.getDataBeginRowIndex() < 0){
                    throw new IllegalArgumentException("sheet config dataBeginRowIndex is null");
                }
            }
        }
        return config;
    }

    /**
     * 获取Sheet所在的index列表
     *
     * @param sheetConfig
     * @return
     */
    public static List<Integer> getSheetIndexList(Workbook workbook, ExcelReaderSheetConfig sheetConfig) {
        Set<Integer> indexSet = new HashSet<>();
        for (int i = sheetConfig.getSheetIndexBegin(); i <= sheetConfig.getSheetIndexEnd(); i++) {
            indexSet.add(i);
        }
        if (sheetConfig.getSheetNames() != null && sheetConfig.getSheetNames().size() > 0) {
            for (String sheetName : sheetConfig.getSheetNames()) {
                int sheetIndex = workbook.getSheetIndex(sheetName);
                if (sheetIndex < 0) {
                    throw new SheetNameNotFoundError(sheetName);
                }
                indexSet.add(sheetIndex);
            }
        }
        List<Integer> indexList = new ArrayList<>(indexSet);
        return indexList;
    }

}
