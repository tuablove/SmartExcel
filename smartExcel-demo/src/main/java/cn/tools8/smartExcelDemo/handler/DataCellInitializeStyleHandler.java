package cn.tools8.smartExcelDemo.handler;

import cn.tools8.smartExcel.handler.IWriteDataCellInitializeStyleHandler;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.interfaces.IExcelWriteCellStyleManager;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 * @author tuaobin 2023/11/10$ 17:40$
 */
public class DataCellInitializeStyleHandler implements IWriteDataCellInitializeStyleHandler {
    @Override
    public void onCreating(IExcelWriteCellStyleManager cellStyleManager, IExcelCellStyleCreator creator) {
        cellStyleManager.addCellStyle("green", createStyle(creator, HSSFColor.HSSFColorPredefined.GREEN.getColor().getIndex()));
        cellStyleManager.addCellStyle("red", createStyle(creator, HSSFColor.HSSFColorPredefined.RED.getColor().getIndex()));
    }

    private CellStyle createStyle(IExcelCellStyleCreator creator, short color) {
        CellStyle cellStyle = creator.newCellStyle();
        Font font = creator.newCellFont();
        font.setColor(color);
        cellStyle.setFont(font);
        return cellStyle;
    }
}
