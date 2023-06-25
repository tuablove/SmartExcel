package cn.tools8.smartExcel.manager;

import cn.tools8.smartExcel.interfaces.IExcelWriteCellStyleManager;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * 单元格样式管理
 * @author tuaobin 2023/6/20$ 17:56$
 */
public class ExcelWriteCellStyleManager implements IExcelWriteCellStyleManager {
    private Map<String, CellStyle> cellStyleMap;

    public ExcelWriteCellStyleManager() {
    }
    @Override
    public void addCellStyle(String type, CellStyle cellStyle) {
        if (cellStyleMap == null) {
            cellStyleMap = new HashMap<>();
        }
        cellStyleMap.put(type, cellStyle);
    }
    @Override
    public CellStyle getCellStyle(String type) {
        if (cellStyleMap != null && cellStyleMap.containsKey(type)) {
            return cellStyleMap.get(type);
        }
        return null;
    }
}
