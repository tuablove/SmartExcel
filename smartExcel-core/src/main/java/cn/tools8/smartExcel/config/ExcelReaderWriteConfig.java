package cn.tools8.smartExcel.config;

import cn.tools8.smartExcel.handler.IReadRowHandler;

/**
 * 读取excel配置
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReaderWriteConfig extends ExcelReaderConfig {
    /**
     * 输出文件地址
     */
    private String filePath;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
