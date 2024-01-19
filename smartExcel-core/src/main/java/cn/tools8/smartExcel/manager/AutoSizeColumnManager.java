package cn.tools8.smartExcel.manager;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.SheetUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动列宽管理
 *
 * @author tuaobin 2023/6/26$ 13:56$
 */
public class AutoSizeColumnManager {
    private Map<Integer, Integer> columnLengthMap = new HashMap<>();

    /**
     * 设置最大值
     *
     * @param column
     * @param length
     */
    public void setMax(Integer column, Integer length) {
        if (column != null && length != null) {
            Integer originLength = columnLengthMap.get(column);
            if (originLength == null) {
                originLength = 0;
            }
            columnLengthMap.put(column, Math.max(originLength, length));
        }
    }

    /**
     * 自动宽度
     *
     * @param sheet
     */
    public void autoSizeColumn(Sheet sheet) {
        for (Map.Entry<Integer, Integer> columnLength : columnLengthMap.entrySet()) {
            sheet.setColumnWidth(columnLength.getKey(), Math.min(Math.max(columnLength.getValue()*2, 8), 255) * 256);
        }
    }
}
