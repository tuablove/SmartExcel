package cn.tools8.smartExcelDemo.handler;

import cn.tools8.smartExcel.entity.ChildTitleCellStyleData;
import cn.tools8.smartExcel.handler.IWriteChildTitleCellStyleHandler;
import org.apache.poi.ss.usermodel.*;

/**
 * 子标题分组样式示例
 *
 * @author tuaobin
 */
public class ChildTitleCellStyleHandler implements IWriteChildTitleCellStyleHandler {
    private static final short[] COLORS = new short[]{
            IndexedColors.CORNFLOWER_BLUE.getIndex(),
            IndexedColors.DARK_TEAL.getIndex(),
            IndexedColors.DARK_YELLOW.getIndex(),
            IndexedColors.VIOLET.getIndex()
    };

    @Override
    public CellStyle onCreating(ChildTitleCellStyleData data) {
        String styleKey = "childTitle_" + (data.getChildIndex() + 1);
        CellStyle style = data.getStyleManager().getCellStyle(styleKey);
        if (style != null) {
            return style;
        }
        style = data.getStyleCreator().newCellStyle();
        style.cloneStyleFrom(data.getDefaultCellStyle());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(COLORS[data.getChildIndex() % COLORS.length]);
        Font font = data.getStyleCreator().newCellFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        style.setFont(font);
        data.getStyleManager().addCellStyle(styleKey, style);
        return style;
    }
}
