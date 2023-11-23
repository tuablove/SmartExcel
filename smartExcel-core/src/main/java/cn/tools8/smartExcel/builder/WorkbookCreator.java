package cn.tools8.smartExcel.builder;

import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.enums.ExcelTypeEnum;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;

/**
 * 创建不同版本的excel
 * @author tuaobin 2023/6/19$ 17:23$
 */
public class WorkbookCreator {
    public static Workbook createWorkbook(ExcelWriteConfig config) throws IOException {
        Workbook workbook = null;
        switch (config.getExcelType()) {
            case EXCEL97:
                workbook = WorkbookFactory.create(false);
                break;
            case EXCEL2007:
                workbook = WorkbookFactory.create(true);
                break;
            case EXCEL2007STREAM:
            default:
                workbook = new SXSSFWorkbook(-1);
                break;
        }
        return workbook;
    }
}
