package cn.tools8.smartExcel;

import cn.tools8.convert.BaseTypeConverter;
import cn.tools8.smartExcel.annotaion.ExcelExport;
import cn.tools8.smartExcel.annotaion.ExcelImport;
import cn.tools8.smartExcel.annotaion.ExcelStyle;
import cn.tools8.smartExcel.builder.WorkbookCreator;
import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.config.ExcelReaderSheetConfig;
import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.CellData;
import cn.tools8.smartExcel.entity.DynamicColumn;
import cn.tools8.smartExcel.entity.ImportField;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.entity.definition.ExcelStyleDefinition;
import cn.tools8.smartExcel.entity.definition.WriteDataFieldDefinition;
import cn.tools8.smartExcel.enums.GenericStyleTypeEnum;
import cn.tools8.smartExcel.handler.*;
import cn.tools8.smartExcel.interfaces.IExcelTitleCellStyleCreator;
import cn.tools8.smartExcel.manager.ExcelWriteCellStyleManager;
import cn.tools8.smartExcel.manager.ExpressionManager;
import cn.tools8.smartExcel.utils.CellUtils;
import cn.tools8.smartExcel.utils.ExcelMergeUtils;
import cn.tools8.smartExcel.utils.ExcelReaderConfigUtils;
import cn.tools8.smartExcel.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.misc.ReflectUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * excel写入类
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelWriter<T extends WriteDataBase> extends AbstractExcel implements IExcelTitleCellStyleCreator {
    private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
    private final ExcelWriteCellStyleManager genericCellStyleManager = new ExcelWriteCellStyleManager();
    private final ExcelWriteCellStyleManager titleCellStyleManager = new ExcelWriteCellStyleManager();
    private final ExcelWriteCellStyleManager dataCellStyleManager = new ExcelWriteCellStyleManager();
    private final ExpressionManager expressionManager = new ExpressionManager();

    public void write(List<? extends WriteDataBase> dataList, ExcelWriteConfig config) throws Exception {
        try {
            workbook = WorkbookCreator.createWorkbook(config);
            genericCellStyleInit(config.getGenericCellStyleHandler());
            IWriteTitleCellStyleHandler titleCellStyleHandler = config.getTitleCellStyleHandler();
            if (titleCellStyleHandler != null) {
                titleCellStyleHandler.onCreating(this);
            }
            if (config.getTitleExpressionHandler() != null) {
                expressionManager.setTitleExpressionHandler(config.getTitleExpressionHandler());
            }
            List<WriteDataFieldDefinition> mainDataFields = extractDataFields(clazz, dataList.size() > 0 ? dataList.get(0) : null);
            List<WriteDataFieldDefinition> childDataFields = null;
            if (dataList.size() > 0) {
                List<? extends WriteDataBase> writeDateChildren = dataList.get(0).getWriteDateChildren();
                childDataFields = extractDataFields(writeDateChildren.get(0).getClass(), writeDateChildren.get(0));
            } else {
                childDataFields = new ArrayList<>();
            }
            int maxTitleRowCount = 0;
            for (WriteDataFieldDefinition mainDataField : mainDataFields) {
                maxTitleRowCount = Math.max(mainDataField.getTitleNames() == null ? 0 : mainDataField.getTitleNames().size(), maxTitleRowCount);
            }
            for (WriteDataFieldDefinition childDataField : childDataFields) {
                maxTitleRowCount = Math.max(childDataField.getTitleNames() == null ? 0 : childDataField.getTitleNames().size(), maxTitleRowCount);
            }
            Sheet sheet = workbook.createSheet(config.getDefaultSheetName());
            createSheetTitle(dataList, mainDataFields, childDataFields, maxTitleRowCount, sheet);
            int maxRows = config.getExcelType().getConfig().getMaxRows() - maxTitleRowCount;
            int maxSheetCount = dataList.size() / maxRows + 1;
            for (int i = 0; i < maxSheetCount; i++) {
                if (i > 0) {
                    sheet = workbook.createSheet(config.getDefaultSheetName() + i);
                    createSheetTitle(dataList, mainDataFields, childDataFields, maxTitleRowCount, sheet);
                }
                int pageSize = Math.min(dataList.size() - i * maxRows, maxRows);
                for (int rowIndex = 0; rowIndex < pageSize; rowIndex++) {
                    Row row = sheet.createRow(rowIndex + maxTitleRowCount);
                    WriteDataBase dataBase = dataList.get(i * pageSize + rowIndex);
                    for (int column = 0; column < mainDataFields.size(); column++) {
                        WriteDataFieldDefinition dataField = mainDataFields.get(column);
                        Cell cell = row.createCell(column);
                        CellStyle cellStyle = null;
                        Object originValue = dataBase.getFieldValue(dataField.getKey());
                        Object cellValue = null;
                        if (dataField.getWriteValueConverterInstance() != null) {
                            cellValue = dataField.getWriteValueConverterInstance().convert(cell, originValue, originValue.getClass());
                        } else {
                            cellValue = originValue;
                        }
                        cell.setCellValue(cellValue.toString());
                        ExcelStyleDefinition styleDefinition = dataField.getStyleDefinition();
                        if (styleDefinition != null) {
                            if (styleDefinition.getCellStyleHandler() != null) {
                                cellStyle = styleDefinition.getCellStyleHandler().onCreating(new CellData(cell, dataBase, originValue, cellValue, dataCellStyleManager, this));
                            } else {
                                String dataFormatStr = styleDefinition.getDataFormat();
                                if (dataFormatStr != null && !dataFormatStr.equals("")) {
                                    CellStyle cellStyleTemp = genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.CONTENT.getType());
                                    CellStyle newCellStyle = dataCellStyleManager.getCellStyle(dataFormatStr);
                                    if (newCellStyle == null) {
                                        newCellStyle = newCellStyle();
                                        newCellStyle.cloneStyleFrom(cellStyleTemp);
                                        DataFormat dataFormat = newDataFormat();
                                        short formatIndex = dataFormat.getFormat(dataFormatStr);
                                        newCellStyle.setDataFormat(formatIndex);
                                        dataCellStyleManager.addCellStyle(dataFormatStr, newCellStyle);
                                    }
                                    cellStyle = newCellStyle;
                                }
                            }
                        }
                        if (cellStyle == null) {
                            cellStyle = genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.CONTENT.getType());
                        }
                        cell.setCellStyle(cellStyle);
                    }
                    if (dataBase.getWriteDateChildren() != null && dataBase.getWriteDateChildren().size() > 0) {
                        for (int count = 0; count < dataBase.getWriteDateChildren().size(); count++) {
                            for (int column = 0; column < childDataFields.size(); column++) {
                                WriteDataFieldDefinition dataField = childDataFields.get(column);
                                Cell cell = row.createCell(column + mainDataFields.size() + count * childDataFields.size());
                                CellStyle cellStyle = genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.CONTENT.getType());
                                Object fieldValue = dataBase.getWriteDateChildren().get(count).getFieldValue(dataField.getKey());
                                cell.setCellValue(fieldValue.toString());
                                cell.setCellStyle(cellStyle);
                            }
                        }
                    }
                }
            }
            try (OutputStream stream = new FileOutputStream(config.getFilePath())) {
                workbook.write(stream);
                stream.flush();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.close(workbook);
        }
    }

    private void createSheetTitle(List<? extends WriteDataBase> dataList, List<WriteDataFieldDefinition> mainDataFields, List<WriteDataFieldDefinition> childDataFields, int maxTitleRowCount, Sheet sheet) {
        for (int i = 0; i < maxTitleRowCount; i++) {
            sheet.createRow(i);
        }
        for (int column = 0; column < mainDataFields.size(); column++) {
            WriteDataFieldDefinition dataField = mainDataFields.get(column);
            List<String> titleNames = dataField.getTitleNames();
            for (int i = 0; i < maxTitleRowCount; i++) {
                int row = maxTitleRowCount - 1 - i;
                String titleName = titleNames.get(0);
                if (titleNames.size() > i) {
                    titleName = titleNames.get(titleNames.size() - i - 1);
                }
                Cell cell = sheet.getRow(row).createCell(column);
                CellStyle cellStyle = titleCellStyleManager.getCellStyle(titleName);
                if (cellStyle == null) {
                    cellStyle = genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.TITLE.getType());
                }
                if (expressionManager.hasExpression(titleName)) {
                    titleName = expressionManager.parse(titleName).toString();
                }
                cell.setCellValue(titleName);
                cell.setCellStyle(cellStyle);
            }
        }
        int maxChildrenCount = 0;
        if (childDataFields.size() > 0) {
            for (WriteDataBase dataBase : dataList) {
                maxChildrenCount = Math.max(maxChildrenCount, dataBase.getWriteDateChildren().size());
            }
            for (int count = 0; count < maxChildrenCount; count++) {
                expressionManager.put("writeDateChildrenIndex", count + 1);
                for (int column = 0; column < childDataFields.size(); column++) {
                    WriteDataFieldDefinition dataField = childDataFields.get(column);
                    List<String> titleNames = dataField.getTitleNames();
                    for (int i = 0; i < maxTitleRowCount; i++) {
                        int row = maxTitleRowCount - 1 - i;
                        String titleName = titleNames.get(0);
                        if (titleNames.size() > i) {
                            titleName = titleNames.get(titleNames.size() - i - 1);
                        }
                        Cell cell = sheet.getRow(row).createCell(column + mainDataFields.size() + count * childDataFields.size());
                        CellStyle cellStyle = titleCellStyleManager.getCellStyle(titleName);
                        if (cellStyle == null) {
                            cellStyle = genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.TITLE.getType());
                        }
                        if (expressionManager.hasExpression(titleName)) {
                            titleName = expressionManager.parse(titleName).toString();
                        }
                        cell.setCellValue(titleName);
                        cell.setCellStyle(cellStyle);
                    }
                }
            }
        }
        ExcelMergeUtils.mergeRange(sheet, new CellRangeAddress(0, maxTitleRowCount - 1, 0, mainDataFields.size() + maxChildrenCount * childDataFields.size() - 1));
    }

    private void genericCellStyleInit(IWriteGenericCellStyleHandler styleHandler) {
        for (GenericStyleTypeEnum styleTypeEnum : GenericStyleTypeEnum.values()) {
            CellStyle style = null;
            if (styleHandler != null) {
                style = styleHandler.onCreated(styleTypeEnum, this);
            }
            if (style == null) {
                switch (styleTypeEnum) {
                    case CONTENT:
                        style = this.newCellStyle();
                        style.setAlignment(HorizontalAlignment.CENTER);
                        style.setVerticalAlignment(VerticalAlignment.CENTER);
                        style.setBorderRight(BorderStyle.THIN);
                        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderLeft(BorderStyle.THIN);
                        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderTop(BorderStyle.THIN);
                        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderBottom(BorderStyle.THIN);
                        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        Font dataFont = this.newCellFont();
                        dataFont.setFontName("Arial");
                        dataFont.setFontHeightInPoints((short) 10);
                        style.setFont(dataFont);
                        break;
                    case TITLE:
                        style = this.newCellStyle();
                        style.setBorderRight(BorderStyle.THIN);
                        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderLeft(BorderStyle.THIN);
                        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderTop(BorderStyle.THIN);
                        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setBorderBottom(BorderStyle.THIN);
                        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        style.setAlignment(HorizontalAlignment.CENTER);
                        style.setVerticalAlignment(VerticalAlignment.CENTER);
                        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
//                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        Font headerFont = this.newCellFont();
                        headerFont.setFontName("Arial");
                        headerFont.setFontHeightInPoints((short) 10);
                        headerFont.setBold(true);
                        headerFont.setColor(IndexedColors.BLACK.getIndex());
                        style.setFont(headerFont);
                        break;
                }
            }
            genericCellStyleManager.addCellStyle(styleTypeEnum.getType(), style);
        }
    }


    private List<WriteDataFieldDefinition> extractDataFields(Class<? extends WriteDataBase> clazz, WriteDataBase writeDataBase) throws InstantiationException, IllegalAccessException {
        List<WriteDataFieldDefinition> dataFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ExcelExport excelExport = field.getAnnotation(ExcelExport.class);
            if (excelExport != null) {
                WriteDataFieldDefinition dataField = new WriteDataFieldDefinition();
                dataField.setKey(field.getName());
                dataField.setOrder(excelExport.order());
                dataField.setTitleNames(Arrays.asList(excelExport.names()));
                if (!excelExport.converter().isInterface() && !Modifier.isAbstract(excelExport.converter().getModifiers())) {
                    dataField.setWriteValueConverter(excelExport.converter());
                    IWriteValueConverter newInstance = excelExport.converter().newInstance();
                    dataField.setWriteValueConverterInstance(newInstance);
                }
                dataField.setField(field);
                dataField.setFieldType(field.getType());
                ExcelStyle excelStyle = field.getAnnotation(ExcelStyle.class);
                if (excelStyle != null) {
                    ExcelStyleDefinition styleDefinition = new ExcelStyleDefinition();
                    styleDefinition.setDataFormat(excelStyle.dataFormat());
                    Class<? extends IWriteDataCellStyleHandler> styleHandler = excelStyle.cellStyleHandler();
                    if (!styleHandler.isInterface() && !Modifier.isAbstract(styleHandler.getModifiers())) {
                        styleDefinition.setCellStyleHandler(styleHandler.newInstance());
                    }
                    dataField.setStyleDefinition(styleDefinition);
                }
                dataFields.add(dataField);
            }
        }
        if (writeDataBase != null) {
            for (DynamicColumn dynamicColumn : writeDataBase) {
                WriteDataFieldDefinition dataField = new WriteDataFieldDefinition();
                dataField.copyFrom(dynamicColumn);
                dataFields.add(dataField);
            }
        }
        dataFields.sort(Comparator.comparing(WriteDataFieldDefinition::getOrder));
        return dataFields;
    }


    /**
     * 目标类
     */
    private Class<T> clazz;

    /**
     * 初始化
     *
     * @param clazz
     */
    public ExcelWriter(Class<T> clazz) {
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

    @Override
    public void addTitleCellStyle(String titleName, CellStyle cellStyle) {
        if (titleName == null || titleName == "") {
            //todo throw
            return;
        }
        titleCellStyleManager.addCellStyle(titleName, cellStyle);
    }
}
