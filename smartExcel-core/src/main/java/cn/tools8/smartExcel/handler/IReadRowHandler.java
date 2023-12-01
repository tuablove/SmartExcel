package cn.tools8.smartExcel.handler;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * 读取到的行
 * @author tuaobin 2023/6/16$ 16:23$
 */
public interface IReadRowHandler {
    /**
     * 读取的行的数据
     * @param sheetIndex sheet的索引
     * @param row 行的索引
     * @param dataRow excel行
     * @param dataList 已经读取行的数据<T>
     * @param rowData 行的数据<T>
     * @return
     */
    <T> void onData(Integer sheetIndex, Integer row, Row dataRow, List<T> dataList, T rowData);
}
