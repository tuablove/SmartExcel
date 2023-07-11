package cn.tools8.smartExcel;

import cn.tools8.convert.BaseTypeConverter;
import cn.tools8.smartExcel.annotaion.ExcelImport;
import cn.tools8.smartExcel.annotaion.ExcelImportValidateMessage;
import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.config.ExcelReaderSheetConfig;
import cn.tools8.smartExcel.entity.ImportField;
import cn.tools8.smartExcel.entity.ValidateResult;
import cn.tools8.smartExcel.handler.IReadValueConverter;
import cn.tools8.smartExcel.manager.validator.PropertyMessageInterpolator;
import cn.tools8.smartExcel.utils.CellUtils;
import cn.tools8.smartExcel.utils.ExcelReaderConfigUtils;
import cn.tools8.smartExcel.utils.IOUtils;
import cn.tools8.smartExcel.utils.ValidatorUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.groups.Default;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * excel读取类
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReader<T> extends AbstractExcel {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

    /**
     * 目标类
     */
    private Class<T> clazz;
    /**
     * 标记为@ExcelImportValidateMessage 的属性
     */
    private List<Field> validateMessageFields;

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
     * @param is excel文件数据流
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<T> read(InputStream is) throws IOException, InstantiationException, IllegalAccessException {
        return read(is, null);
    }

    /**
     * 读取excel
     *
     * @param is     excel文件数据流
     * @param config 读取文件配置
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<T> read(InputStream is, ExcelReaderConfig config) throws IOException, InstantiationException, IllegalAccessException {
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
                    Map<String, ImportField> fieldMap = columnFieldMap.values().stream().collect(Collectors.toMap(ImportField::getName, item -> item));
                    for (int rowIndex = sheetConfig.getDataBeginRowIndex(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                        Row dataRow = sheet.getRow(rowIndex);
                        if (dataRow == null) {
                            continue;
                        }
                        Object entity = null;
                        entity = clazz.newInstance();
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
                                    try {
                                        value = BaseTypeConverter.convert(cellValue, field.getType());
                                    } catch (Exception ignore) {

                                    }
                                }
                            }
                            if (value != null) {
                                field.set(entity, value);
                                filled = true;
                            }
                        }
                        if (filled) {
                            validateEntity(config, sheetIndex, fieldMap, rowIndex, entity);
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
     * 验证对象
     *
     * @param config
     * @param sheetIndex
     * @param fieldMap
     * @param rowIndex
     * @param entity
     */
    private void validateEntity(ExcelReaderConfig config, Integer sheetIndex, Map<String, ImportField> fieldMap, int rowIndex, Object entity) throws IllegalAccessException {
        if (config.isValidate()) {
            Map<String, List<String>> errorMessage = null;
            PropertyMessageInterpolator propertyInterpolator = new PropertyMessageInterpolator(new PropertyMessageInterpolator.PropertyNameObtainHandler() {
                @Override
                public String obtain(String property) {
                    ImportField importField = fieldMap.get(property);
                    if (importField != null) {
                        return importField.getColumnName();
                    }
                    return "";
                }
            });
            if (config.getValidateGroups() == null) {
                errorMessage = ValidatorUtil.validate(entity, propertyInterpolator, Default.class);
            } else {
                errorMessage = ValidatorUtil.validate(entity, propertyInterpolator, config.getValidateGroups());
            }
            if (errorMessage != null && errorMessage.size() > 0) {
                if (config.getValidateExcludeFields() != null && config.getValidateExcludeFields().size() > 0) {
                    for (String excludeFieldName : config.getValidateExcludeFields()) {
                        errorMessage.remove(excludeFieldName);
                    }
                }
                if (errorMessage.size() > 0) {
                    ValidateResult validateResult = new ValidateResult();
                    validateResult.setSheetIndex(sheetIndex);
                    validateResult.setRow(rowIndex);
                    validateResult.setRowData(entity);
//                    for (Map.Entry<String, List<String>> errorMessageEntry : errorMessage.entrySet()) {
//                        ImportField importField = fieldMap.get(errorMessageEntry.getKey());
//                        if (importField != null) {
//                            for (int i = 0; i < errorMessageEntry.getValue().size(); i++) {
//                                errorMessageEntry.getValue().set(i, errorMessageEntry.getValue().get(i).replaceAll("\\{name\\}", importField.getColumnName()));
//                            }
//                        }
//                    }
                    config.getValidateResults().add(validateResult);
                    if (validateMessageFields != null && validateMessageFields.size() > 0) {
                        String validateMessageSingle = errorMessage.values().stream()
                                .flatMap(List::stream)
                                .collect(Collectors.joining(","));
                        List<String> validateMessageList = errorMessage.values().stream()
                                .flatMap(List::stream)
                                .collect(Collectors.toList());
                        //写入对象指定字段
                        for (Field validateMessageField : validateMessageFields) {
                            if (validateMessageField.getType().isAssignableFrom(String.class)) {
                                validateMessageField.set(entity, validateMessageSingle);
                            } else if (List.class.isAssignableFrom(validateMessageField.getType())) {
                                try {
                                    validateMessageField.set(entity, validateMessageList);
                                } catch (Exception ignore) {

                                }

                            }
                        }
                    }
                }
            }
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
        validateMessageFields = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            Annotation validateMessageAnnotation = declaredField.getAnnotation(ExcelImportValidateMessage.class);
            if (validateMessageAnnotation != null) {
                declaredField.setAccessible(true);
                validateMessageFields.add(declaredField);
            }
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
                        columnFieldMap.put(column, new ImportField(declaredField.getName(), declaredField, converter, name));
                    }
                }
            }
            if (excelImport.columnString() != null && !excelImport.columnString().equals("")) {
                int column = CellReference.convertColStringToIndex(excelImport.columnString());
                if (column > 0) {
                    columnFieldMap.put(new Integer(column).shortValue(), new ImportField(declaredField.getName(), declaredField, converter, excelImport.columnString()));
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
