package cn.tools8.smartExcel.config;

import java.util.List;

/**
 * 读取excel单sheet配置
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReaderSheetConfig {
    /**
     * 读取的sheet页开始索引
     */
    private Integer sheetIndexBegin;
    /**
     * 读取的sheet页结束索引
     */
    private Integer sheetIndexEnd;
    /**
     * 读取的sheet页名称
     */
    private List<String> sheetNames;
    /**
     * 标题匹配的行索引
     */
    private Integer titleRowIndex;
    /**
     * 数据开始的行索引
     */
    private Integer dataBeginRowIndex;

    public Integer getSheetIndexBegin() {
        return sheetIndexBegin;
    }

    public void setSheetIndexBegin(Integer sheetIndexBegin) {
        this.sheetIndexBegin = sheetIndexBegin;
    }

    public Integer getSheetIndexEnd() {
        return sheetIndexEnd;
    }

    public void setSheetIndexEnd(Integer sheetIndexEnd) {
        this.sheetIndexEnd = sheetIndexEnd;
    }

    public List<String> getSheetNames() {
        return sheetNames;
    }

    public void setSheetNames(List<String> sheetNames) {
        this.sheetNames = sheetNames;
    }

    public Integer getTitleRowIndex() {
        return titleRowIndex;
    }

    public void setTitleRowIndex(Integer titleRowIndex) {
        this.titleRowIndex = titleRowIndex;
    }

    public Integer getDataBeginRowIndex() {
        return dataBeginRowIndex;
    }

    public void setDataBeginRowIndex(Integer dataBeginRowIndex) {
        this.dataBeginRowIndex = dataBeginRowIndex;
    }

    public ExcelReaderSheetConfig() {
    }

    public ExcelReaderSheetConfig(Integer sheetIndexBegin, Integer sheetIndexEnd, List<String> sheetNames, Integer titleRowIndex, Integer dataBeginRowIndex) {
        this.sheetIndexBegin = sheetIndexBegin;
        this.sheetIndexEnd = sheetIndexEnd;
        this.sheetNames = sheetNames;
        this.titleRowIndex = titleRowIndex;
        this.dataBeginRowIndex = dataBeginRowIndex;
    }
}
