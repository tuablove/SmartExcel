package cn.tools8.smartExcel.reader;

import cn.tools8.convert.BaseTypeConverter;
import cn.tools8.smartExcel.AbstractExcel;
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
import cn.tools8.smartExcel.utils.ReflectUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 读取excel
 * @author tuaobin 2023/12/13$ 19:21$
 */
public class AbstractExcelReader extends AbstractExcel {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExcelReader.class);
    protected  <T> List<T>  doRead(InputStream is, ExcelReaderConfig config,Class<?> clazz) throws IOException, InstantiationException, IllegalAccessException {
        workbook = WorkbookFactory.create(is, config.getPassword());
        int sheetCount = workbook.getNumberOfSheets();
        List<T> dataList = new ArrayList<>();
        // 标记为@ExcelImportValidateMessage 的属性
        List<Field> validateMessageFields=new ArrayList<>();;
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
                Map<Short, ImportField> columnFieldMap = getColumn2ClassFieldMap(clazz,validateMessageFields, titleColumnMap,config.getGroups());
                Map<String, ImportField> fieldMap = columnFieldMap.values().stream().collect(Collectors.toMap(ImportField::getName, item -> item));
                PropertyMessageInterpolator propertyInterpolator = null;
                if (config.isValidate()) {
                    propertyInterpolator = new PropertyMessageInterpolator(new PropertyMessageInterpolator.PropertyNameObtainHandler() {
                        @Override
                        public String obtain(String property) {
                            ImportField importField = fieldMap.get(property);
                            return importField == null ? "" : importField.getColumnName();
                        }
                    });
                }
                for (int rowIndex = sheetConfig.getDataBeginRowIndex(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row dataRow = sheet.getRow(rowIndex);
                    if (dataRow == null) {
                        continue;
                    }
                    Object entity = null;
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
                            value = converter.convert(cell, cellValue, cellValue == null ? Object.class : cellValue.getClass());
                        } else {
                            if (cellValue != null && !cellValue.equals("")) {
                                try {
                                    value = BaseTypeConverter.convert(cellValue, field.getType());
                                } catch (Exception ignore) {

                                }
                            }
                        }
                        if (value != null) {
                            if (entity == null) {
                                entity = clazz.newInstance();
                            }
                            field.set(entity, value);
                            filled = true;
                        }
                    }
                    if (filled && (config.getRowIgnoreHandler() == null || !config.getRowIgnoreHandler().ignore(sheetIndex, rowIndex, entity))) {
                        validateEntity(config,validateMessageFields, propertyInterpolator, sheetIndex, fieldMap, rowIndex, entity);
                        if(config.getReadRowHandler()!=null) {
                            config.getReadRowHandler().onData(sheetIndex, rowIndex, dataRow,dataList, (T) entity);
                        }
                        if (entity != null) {
                            dataList.add((T) entity);
                        }
                    }
                }
            }
        }
        return dataList;
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
    protected void validateEntity(ExcelReaderConfig config,List<Field> validateMessageFields, PropertyMessageInterpolator propertyInterpolator, Integer sheetIndex, Map<String, ImportField> fieldMap, int rowIndex, Object entity) throws IllegalAccessException {
        if (config.isValidate()) {
            Map<String, List<String>> errorMessage = null;
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
                    config.getValidateResults().add(validateResult);
                    if (validateMessageFields != null && validateMessageFields.size() > 0) {
                        //写入对象指定字段
                        for (Field validateMessageField : validateMessageFields) {
                            if (validateMessageField.getType().isAssignableFrom(String.class)) {
                                String validateMessageSingle = errorMessage.values().stream()
                                        .flatMap(List::stream)
                                        .collect(Collectors.joining(","));
                                setEntityValue(validateMessageField, entity, validateMessageSingle);
                            } else if (List.class.isAssignableFrom(validateMessageField.getType())) {
                                List<String> validateMessageList = errorMessage.values().stream()
                                        .flatMap(List::stream)
                                        .collect(Collectors.toList());
                                setEntityValue(validateMessageField, entity, validateMessageList);
                            } else if (Map.class.isAssignableFrom(validateMessageField.getType())) {
                                setEntityValue(validateMessageField, entity, errorMessage);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 设置对象值
     * @param validateMessageField
     * @param entity
     * @param errorMessage
     */
    private static void setEntityValue(Field validateMessageField, Object entity, Object errorMessage) {
        try {
            validateMessageField.set(entity, errorMessage);
        } catch (Exception ex) {
            logger.warn("设置对象值失败", ex);
        }
    }

    /**
     * 获取列索引与目标属性的对应map
     *
     * @param titleColumnMap
     * @param groups
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected Map<Short, ImportField> getColumn2ClassFieldMap(Class<?> clazz, List<Field> validateMessageFields, Map<String, Short> titleColumnMap, Class<?>[] groups) throws InstantiationException, IllegalAccessException {
        validateMessageFields.clear();
        Map<Short, ImportField> columnFieldMap = new HashMap<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Annotation validateMessageAnnotation = declaredField.getAnnotation(ExcelImportValidateMessage.class);
            if (validateMessageAnnotation != null) {
                declaredField.setAccessible(true);
                validateMessageFields.add(declaredField);
            }
            ExcelImport excelImport = ReflectUtil.getAnnotationWithGroups(declaredField,ExcelImport.class,groups);
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
    protected Map<String, Short> getTitle2ColumnIndexMap(Row titleRow, short minColIx, short maxColIx) {
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
