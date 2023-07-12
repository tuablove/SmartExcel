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
     * 读取excel
     *
     * @param is excel文件数据流
     * @return
     * @throws IOException
     */
    public List<String> read(InputStream is) throws Exception {
        return read(is, null);
    }

    /**
     * 读取excel
     *
     * @param is     excel文件数据流
     * @param config 读取文件配置
     * @return
     * @throws IOException
     */
    public List<String> read(InputStream is, ExcelReaderConfig config) throws Exception {
        List<String> titles = new ArrayList<>();
        try {
            config = ExcelReaderConfigUtils.validateConfig(config);
            workbook = WorkbookFactory.create(is, config.getPassword());
            int sheetCount = workbook.getNumberOfSheets();
            ExcelReaderSheetConfig sheetConfig = config.getSheetConfigs().get(0);
            if (sheetConfig.getSheetIndexBegin() >= sheetCount) {
                return titles;
            }
            if (sheetConfig.getSheetIndexEnd() == null) {
                sheetConfig.setSheetIndexEnd(sheetConfig.getSheetIndexBegin());
            }
            sheetConfig.setSheetIndexEnd(Math.min(sheetConfig.getSheetIndexEnd(), sheetCount - 1));
            List<Integer> indexList = ExcelReaderConfigUtils.getSheetIndexList(workbook, sheetConfig);
            indexList.sort(Integer::compareTo);
            Sheet sheet = workbook.getSheetAt(indexList.get(0));
            Row titleRow = sheet.getRow(sheetConfig.getTitleRowIndex());
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
