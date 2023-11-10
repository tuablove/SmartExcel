package cn.tools8.smartExcel.reader;

import cn.tools8.smartExcel.AbstractExcel;
import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.config.ExcelReaderSheetConfig;
import cn.tools8.smartExcel.utils.CellUtils;
import cn.tools8.smartExcel.utils.ExcelReaderConfigUtils;
import cn.tools8.smartExcel.utils.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * excel读取类,只读取首个sheet的标题行
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReaderTitle extends AbstractExcel {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReaderTitle.class);

    /**
     * 读取excel标题行
     *
     * @param file
     * @return
     * @throws Exception
     */
    public List<String> read(String file) throws Exception {
        return read(new FileInputStream(file));
    }

    /**
     * 读取excel标题行
     *
     * @param file
     * @param password
     * @param titleRowIndex
     * @return
     * @throws Exception
     */
    public List<String> read(String file, String password, int titleRowIndex) throws Exception {
        return read(new FileInputStream(file), password, titleRowIndex);
    }

    /**
     * 读取excel标题行
     *
     * @param is excel文件数据流
     * @return
     * @throws IOException
     */
    public List<String> read(InputStream is) throws Exception {
        return read(is, null, 0);
    }

    /**
     * 读取excel标题行
     *
     * @param is            excel文件数据流
     * @param password      excel密码
     * @param titleRowIndex 标题行索引
     * @return
     * @throws IOException
     */
    public List<String> read(InputStream is, String password, int titleRowIndex) throws Exception {
        List<String> titles = new ArrayList<>();
        try {
            workbook = WorkbookFactory.create(is, password);
            int sheetCount = workbook.getNumberOfSheets();
            if (sheetCount == 0) {
                return titles;
            }
            Sheet sheet = workbook.getSheetAt(0);
            Row titleRow = sheet.getRow(titleRowIndex);
            short minColIx = titleRow.getFirstCellNum();
            short maxColIx = titleRow.getLastCellNum();
            for (short column = minColIx; column < maxColIx; column++) {
                Cell cell = titleRow.getCell(column);
                if (cell == null) {
                    continue;
                }
                Object val = CellUtils.getCellValue(cell);
                if (val != null) {
                    titles.add(val.toString());
                }
            }
            return titles;
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.close(workbook);
            IOUtils.close(is);
        }
    }
}
