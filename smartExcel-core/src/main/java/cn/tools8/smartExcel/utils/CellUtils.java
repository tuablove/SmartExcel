package cn.tools8.smartExcel.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.math.BigDecimal;

/**
 * 单元格帮助类
 * @author tuaobin 2023/6/19$ 13:56$
 */
public class CellUtils {
    public static Object getCellValue(Cell cell) {
        Object val = null;
        switch (cell.getCellType()) {
            case _NONE:
                break;
            case STRING:
                val = cell.getStringCellValue();
                break;
            case BOOLEAN:
                val = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    val = cell.getDateCellValue();
                } else {
                    val = BigDecimal.valueOf(cell.getNumericCellValue());
                }
                break;
            case ERROR:
                val = cell.getErrorCellValue();
                break;
            case FORMULA:
                try {
                    val = BigDecimal.valueOf(cell.getNumericCellValue());
                } catch (Exception ignore) {
                    try {
                        val = cell.getStringCellValue();
                    } catch (Exception ignore1) {

                    }
                }
                break;
            case BLANK:
                val = "";
                break;
            default:
                break;
        }
        return val;
    }
}
