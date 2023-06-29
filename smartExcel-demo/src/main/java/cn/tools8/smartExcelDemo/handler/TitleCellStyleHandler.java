package cn.tools8.smartExcelDemo.handler;

import cn.tools8.smartExcel.handler.IWriteTitleCellStyleHandler;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.interfaces.IExcelWriteCellStyleManager;
import org.apache.poi.ss.usermodel.*;

/**
 * @author tuaobin 2023/6/29$ 11:40$
 */
public class TitleCellStyleHandler implements IWriteTitleCellStyleHandler {
    @Override
    public void onCreating(IExcelWriteCellStyleManager cellStyleManager, IExcelCellStyleCreator creator) {
        String key = "学费统计报表";
        CellStyle style = cellStyleManager.getCellStyle(key);
        if (style == null) {
            style = creator.newCellStyle();
            style.setBorderRight(BorderStyle.THIN);
            style.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            style.setBorderLeft(BorderStyle.THIN);
            style.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            style.setBorderTop(BorderStyle.THIN);
            style.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = creator.newCellFont();
            headerFont.setFontName("Arial");
            headerFont.setFontHeightInPoints((short) 20);
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            style.setFont(headerFont);
            cellStyleManager.addCellStyle(key, style);
        }
    }
}
