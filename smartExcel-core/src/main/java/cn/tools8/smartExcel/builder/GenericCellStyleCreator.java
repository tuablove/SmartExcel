package cn.tools8.smartExcel.builder;

import cn.tools8.smartExcel.enums.GenericStyleTypeEnum;
import cn.tools8.smartExcel.handler.IWriteGenericCellStyleHandler;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.manager.ExcelWriteCellStyleManager;
import org.apache.poi.ss.usermodel.*;

/**
 * 通用样式创建
 * @author tuaobin 2023/6/25$ 10:04$
 */
public class GenericCellStyleCreator {


    /**
     * 初始化
     * @param creator
     * @param styleHandler
     * @return
     */
    public static ExcelWriteCellStyleManager create(IExcelCellStyleCreator creator, IWriteGenericCellStyleHandler styleHandler) {
        ExcelWriteCellStyleManager genericCellStyleManager = new ExcelWriteCellStyleManager();
        for (GenericStyleTypeEnum styleTypeEnum : GenericStyleTypeEnum.values()) {
            CellStyle style = null;
            if (styleHandler != null) {
                style = styleHandler.onCreated(styleTypeEnum, creator);
            }
            if (style == null) {
                switch (styleTypeEnum) {
                    case CONTENT:
                        style = creator.newCellStyle();
                        style.setAlignment(HorizontalAlignment.CENTER);
                        style.setVerticalAlignment(VerticalAlignment.CENTER);
                        style.setBorderRight(BorderStyle.THIN);
                        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderLeft(BorderStyle.THIN);
                        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderTop(BorderStyle.THIN);
                        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderBottom(BorderStyle.THIN);
                        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        Font dataFont = creator.newCellFont();
                        dataFont.setFontName("Arial");
                        dataFont.setFontHeightInPoints((short) 10);
                        style.setFont(dataFont);
                        break;
                    case TITLE:
                        style = creator.newCellStyle();
                        style.setBorderRight(BorderStyle.THIN);
                        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderLeft(BorderStyle.THIN);
                        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderTop(BorderStyle.THIN);
                        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderBottom(BorderStyle.THIN);
                        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setAlignment(HorizontalAlignment.CENTER);
                        style.setVerticalAlignment(VerticalAlignment.CENTER);
                        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
//                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        Font headerFont = creator.newCellFont();
                        headerFont.setFontName("Arial");
                        headerFont.setFontHeightInPoints((short) 10);
                        headerFont.setBold(true);
                        headerFont.setColor(IndexedColors.BLACK.getIndex());
                        style.setFont(headerFont);
                        break;
                }
            }
            genericCellStyleManager.addCellStyle(styleTypeEnum.getType(), style);
        }
        return genericCellStyleManager;
    }
}
