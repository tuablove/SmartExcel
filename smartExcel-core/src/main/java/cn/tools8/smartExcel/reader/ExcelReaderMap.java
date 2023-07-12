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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel读取类
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReaderMap extends AbstractExcel {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReaderMap.class);

    /**
     * 读取excel
     *
     * @param is excel文件数据流
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> read(InputStream is) throws Exception {
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
    public List<Map<String, Object>> read(InputStream is, ExcelReaderConfig config) throws Exception{
        try {
            config = ExcelReaderConfigUtils.validateConfig(config);
            workbook = WorkbookFactory.create(is, config.getPassword());
            int sheetCount = workbook.getNumberOfSheets();
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (ExcelReaderSheetConfig sheetConfig : config.getSheetConfigs()) {
                if (sheetConfig.getSheetIndexBegin() >= sheetCount) {
                    continue;
                }
                if (sheetConfig.getSheetIndexEnd() == null) {
                    sheetConfig.setSheetIndexEnd(sheetConfig.getSheetIndexBegin());
                }
                sheetConfig.setSheetIndexEnd(Math.min(sheetConfig.getSheetIndexEnd(), sheetCount - 1));
                List<Integer> indexList = ExcelReaderConfigUtils.getSheetIndexList(workbook, sheetConfig);
                indexList.sort(Integer::compareTo);
                for (Integer sheetIndex : indexList) {
                    Sheet sheet = workbook.getSheetAt(sheetIndex);
                    Row titleRow = sheet.getRow(sheetConfig.getTitleRowIndex());
                    short minColIx = titleRow.getFirstCellNum();
                    short maxColIx = titleRow.getLastCellNum();
                    Map<String, Short> titleColumnMap = getTitle2ColumnIndexMap(titleRow, minColIx, maxColIx);
                    for (int rowIndex = sheetConfig.getDataBeginRowIndex(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row dataRow = sheet.getRow(rowIndex);
                        if (dataRow == null) {
                            continue;
                        }
                        Map<String, Object> dataMap = new HashMap<>();
                        for (Map.Entry<String, Short> titleColumn : titleColumnMap.entrySet()) {
                            String columnName = titleColumn.getKey();
                            Cell cell = dataRow.getCell(titleColumn.getValue());
                            if (cell == null) {
                                continue;
                            }
                            Object cellValue = CellUtils.getCellValue(cell);
                            dataMap.put(columnName, cellValue);
                        }
                        dataList.add(dataMap);
                    }
                }
            }
            return dataList;
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.close(workbook);
            IOUtils.close(is);
        }
    }

    /**
     * 获取标题与列索引的map
     *
     * @param titleRow
     * @param minColIx
     * @param maxColIx
     * @return
     */
    private Map<String, Short> getTitle2ColumnIndexMap(Row titleRow, short minColIx, short maxColIx) {
        Map<String, Short> titleColumnMap = new HashMap<>();
        for (short column = minColIx; column < maxColIx; column++) {
            Cell cell = titleRow.getCell(column);
            if (cell == null) {
                continue;
            }
            Object val = CellUtils.getCellValue(cell);
            if (val != null) {
                titleColumnMap.put(val.toString(), column);
            }
        }
        return titleColumnMap;
    }


}
