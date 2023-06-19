package cn.tools8.smartExcel.config;

import java.util.ArrayList;

/**
 * @author tuaobin 2023/6/15$ 13:47$
 */
public class ExcelReaderConfigBuilder {

    /**
     *
     * @param titleRowIndex
     * @param dataBeginRowIndex
     * @return
     */
    public static ExcelReaderConfig build(Integer titleRowIndex, Integer dataBeginRowIndex) {
        ExcelReaderConfig config = new ExcelReaderConfig();
        config.setSheetConfigs(new ArrayList<>());
        config.getSheetConfigs().add(new ExcelReaderSheetConfig(0, 0, null, titleRowIndex, dataBeginRowIndex));
        return config;
    }
}
