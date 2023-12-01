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
    /**
     * 单行处理
     */
    private IReadRowHandler readRowHandler;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public IReadRowHandler getReadRowHandler() {
        return readRowHandler;
    }

    public void setReadRowHandler(IReadRowHandler readRowHandler) {
        this.readRowHandler = readRowHandler;
    }
}
