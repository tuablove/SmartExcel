package cn.tools8.smartExcel.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 单元格帮助类
 *
 * @author tuaobin 2023/6/19$ 13:56$
 */
public class CellUtils {
    /**
     * 获取单元格数值
     *
     * @param cell
     * @return
     */
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
                    if (cell.getNumericCellValue() % 1 > 0) {
                        val = BigDecimal.valueOf(cell.getNumericCellValue());
                    } else {
                        val = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
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

    /**
     * 设置数据值
     * @param cell
     * @param cellValue
     */
    public static void setCellValue(Cell cell, Object cellValue) {
        if (cellValue == null) {
            return;
        }
        if (cellValue.getClass().isAssignableFrom(String.class)) {
            cell.setCellValue((String) cellValue);
        } else if (cellValue.getClass().isAssignableFrom(Double.class)) {
            cell.setCellValue((Double) cellValue);
        } else if (cellValue.getClass().isAssignableFrom(Integer.class)) {
            cell.setCellValue((Integer) cellValue);
        } else if (cellValue.getClass().isAssignableFrom(Long.class)) {
            cell.setCellValue((Long) cellValue);
        } else if (cellValue.getClass().isAssignableFrom(Short.class)) {
            cell.setCellValue((Short) cellValue);
        } else if (cellValue.getClass().isAssignableFrom(BigDecimal.class)) {
            cell.setCellValue(((BigDecimal) cellValue).doubleValue());
        } else if (cellValue.getClass().isAssignableFrom(Date.class)) {
            cell.setCellValue(((Date) cellValue));
        } else if (cellValue.getClass().isAssignableFrom(Boolean.class)) {
            cell.setCellValue(((Boolean) cellValue));
        } else {
            cell.setCellValue(cellValue.toString());
        }
    }
}
