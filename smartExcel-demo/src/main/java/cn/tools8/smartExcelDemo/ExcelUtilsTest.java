package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.utils.ExcelUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * 只读取excel的标题行
 */
public class ExcelUtilsTest {
    public static void main(String[] args) throws Exception {
//        writeCell();
        changeStyle();
    }

    public static void writeCell() throws Exception {

        String source = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表template.xlsx";
        String target = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表templateWriteCell.xlsx";
        ExcelUtils.copyTo(source, target);
        ExcelUtils.writeCell(target, 0, 2, null, "异常");
    }

    public static void changeStyle() throws Exception {

        String source = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表template.xlsx";
        String target = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表changeStyle.xlsx";
        ExcelUtils.copyTo(source, target);
        ExcelUtils.changeStyle(target, 0, 2, new ExcelUtils.IExcelStyleChangeHandler() {
            @Override
            public CellStyle change(Workbook workbook, Sheet sheet, Row targetRow, short c, Cell cell, Object cellValue, List<CellStyle> cellStyleList) {
                if (cellValue.toString().equals("收入")) {
                    CellStyle cellStyle = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setColor(IndexedColors.GREEN.getIndex());
                    cellStyle.setFont(font);
                    // 设置单元格填充颜色
                    cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
// 设置填充模式为 SOLID_FOREGROUND 才会生效
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    return cellStyle;
                }
                return null;
            }
        });
    }
}
