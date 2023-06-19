package cn.tools8.smartExcel;

import cn.tools8.convert.BaseTypeConverter;
import cn.tools8.smartExcel.annotaion.ExcelImport;
import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.config.ExcelReaderSheetConfig;
import cn.tools8.smartExcel.entity.ImportField;
import cn.tools8.smartExcel.handler.IReadValueConverter;
import cn.tools8.smartExcel.utils.CellUtils;
import cn.tools8.smartExcel.utils.ExcelReaderConfigUtils;
import cn.tools8.smartExcel.utils.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.misc.ReflectUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel读取类
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReader<T> extends AbstractExcel {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

    /**
     * 目标类
     */
    private Class<T> clazz;

    /**
     * 初始化
     *
     * @param clazz
     */
    public ExcelReader(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 读取excel
     *
     * @param is  excel文件数据流
     * @param <T>
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> List<T> read(InputStream is) throws IOException, InstantiationException, IllegalAccessException {
        return read(is, null);
    }

    /**
     * 读取excel
     *
     * @param is     excel文件数据流
     * @param config 读取文件配置
     * @param <T>
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> List<T> read(InputStream is, ExcelReaderConfig config) throws IOException, InstantiationException, IllegalAccessException {
        config = ExcelReaderConfigUtils.validateConfig(config);
        try {
            workbook = WorkbookFactory.create(is, config.getPassword());
            int sheetCount = workbook.getNumberOfSheets();
            List<T> dataList = new ArrayList<>();
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
                    Map<Short, ImportField> columnFieldMap = getColumn2ClassFieldMap(titleColumnMap);
                    for (int rowIndex = sheetConfig.getDataBeginRowIndex(); rowIndex < sheet.getLastRowNum(); rowIndex++) {
                        Row dataRow = sheet.getRow(rowIndex);
                        if (dataRow == null) {
                            continue;
                        }
                        Object entity = null;
                        entity = ReflectUtil.newInstance(clazz);
                        boolean filled = false;
                        for (short column = minColIx; column < maxColIx; column++) {
                            ImportField importField = columnFieldMap.get(column);
                            if (importField == null) {
                                continue;
                            }
                            Cell cell = dataRow.getCell(column);
                            if (cell == null) {
                                continue;
                            }
                            Field field = importField.getField();
                            IReadValueConverter converter = importField.getConverter();
                            Object cellValue = CellUtils.getCellValue(cell);
                            Object value = null;
                            if (converter != null) {
                                value = converter.convert(cell, cellValue, cellValue.getClass());
                            } else {
                                if (cellValue != null && !cellValue.equals("")) {
                                    value = BaseTypeConverter.convert(cellValue, field.getType());
                                }
                            }
                            if (value != null) {
                                field.set(entity, value);
                                filled = true;
                            }
                        }
                        if (filled) {
                            dataList.add((T) entity);
                        }
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
     * 获取列索引与目标属性的对应map
     *
     * @param titleColumnMap
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Map<Short, ImportField> getColumn2ClassFieldMap(Map<String, Short> titleColumnMap) throws InstantiationException, IllegalAccessException {
        Map<Short, ImportField> columnFieldMap = new HashMap<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ExcelImport excelImport = declaredField.getAnnotation(ExcelImport.class);
            if (excelImport == null || ((excelImport.names() == null || excelImport.names().length == 0) && (excelImport.columnString() == null || excelImport.columnString().length() == 0))) {
                continue;
            }
            declaredField.setAccessible(true);
            IReadValueConverter converter = null;
            if (excelImport.converter() != null && !excelImport.converter().isInterface() && !Modifier.isAbstract(excelImport.converter().getModifiers())) {
                converter = excelImport.converter().newInstance();
            }
            if (excelImport.names() != null && excelImport.names().length > 0) {
                for (String name : excelImport.names()) {
                    Short column = titleColumnMap.get(name);
                    if (column != null) {
                        columnFieldMap.put(column, new ImportField(declaredField, converter));
                    }
                }
            }
            if (excelImport.columnString() != null && !excelImport.columnString().equals("")) {
                int column = CellReference.convertColStringToIndex(excelImport.columnString());
                if (column > 0) {
                    columnFieldMap.put(new Integer(column).shortValue(), new ImportField(declaredField, converter));
                }
            }
        }
        return columnFieldMap;
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
