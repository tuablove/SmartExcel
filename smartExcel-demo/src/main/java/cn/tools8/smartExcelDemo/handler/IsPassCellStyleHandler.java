package cn.tools8.smartExcelDemo.handler;

import cn.tools8.smartExcel.entity.CellData;
import cn.tools8.smartExcel.handler.IWriteDataCellStyleHandler;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import static org.apache.poi.ss.usermodel.Font.COLOR_RED;

/**
 * @author tuaobin 2023/6/25$ 14:02$
 */
public class IsPassCellStyleHandler implements IWriteDataCellStyleHandler {
    @Override
    public CellStyle onCreating(CellData cellData) {
        if (cellData.getCellValue() == null) {
            return null;
        }
        Boolean originCellValue = (Boolean) cellData.getOriginCellValue();
        if (originCellValue) {
            String color = "green";
            CellStyle red = cellData.getStyleManager().getCellStyle(color);
            if (red == null) {
                IExcelCellStyleCreator styleCreator = cellData.getStyleCreator();
                red = styleCreator.newCellStyle();
                red.cloneStyleFrom(cellData.getDefaultCellStyle());
                Font font = styleCreator.newCellFont();
                font.setColor(HSSFColor.HSSFColorPredefined.GREEN.getColor().getIndex());
                red.setFont(font);
                cellData.getStyleManager().addCellStyle(color, red);
            }
            return red;
        } else {
            String color = "red";
            CellStyle red = cellData.getStyleManager().getCellStyle(color);
            if (red == null) {
                IExcelCellStyleCreator styleCreator = cellData.getStyleCreator();
                red = styleCreator.newCellStyle();
                red.cloneStyleFrom(cellData.getDefaultCellStyle());
                Font font = styleCreator.newCellFont();
                font.setColor(COLOR_RED);
                red.setFont(font);
                cellData.getStyleManager().addCellStyle(color, red);
            }
            return red;
        }
    }
}
