package cn.tools8.smartExcel.builder;

import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 默认样式创建
 *
 * @author tuaobin 2023/6/25$ 10:04$
 */
public class DefaultCellStyleCreator implements IExcelCellStyleCreator{

    Workbook workbook;

    public DefaultCellStyleCreator(Workbook workbook) {
        this.workbook=workbook;
    }

    @Override
    public CellStyle newCellStyle() {
        return workbook.createCellStyle();
    }

    @Override
    public Font newCellFont() {
        return workbook.createFont();
    }

    @Override
    public DataFormat newDataFormat() {
        return workbook.createDataFormat();
    }
}
