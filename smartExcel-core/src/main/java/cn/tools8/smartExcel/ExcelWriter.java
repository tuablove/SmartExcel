package cn.tools8.smartExcel;

import cn.tools8.smartExcel.builder.*;
import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.CellOriginData;
import cn.tools8.smartExcel.entity.CellData;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.entity.definition.ExcelStyleDefinition;
import cn.tools8.smartExcel.entity.definition.WriteDataFieldDefinition;
import cn.tools8.smartExcel.enums.GenericStyleTypeEnum;
import cn.tools8.smartExcel.manager.AutoSizeColumnManager;
import cn.tools8.smartExcel.manager.ExcelWriteCellStyleManager;
import cn.tools8.smartExcel.manager.ExpressionManager;
import cn.tools8.smartExcel.utils.CellUtils;
import cn.tools8.smartExcel.utils.ExcelMergeUtils;
import cn.tools8.smartExcel.utils.ExcelWriteConfigUtils;
import cn.tools8.smartExcel.utils.IOUtils;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * excel写入类
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelWriter<T> extends AbstractExcel {
    private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
    private DefaultCellStyleCreator defaultCellStyleCreator;
    private ExcelWriteCellStyleManager genericCellStyleManager;
    private ExcelWriteCellStyleManager titleCellStyleManager;
    private DataCellStyleCreator dataCellStyleCreator;
    private ExcelWriteCellStyleManager dataCellStyleManager;

    private final ExpressionManager expressionManager = new ExpressionManager<T>();

    /**
     * 写入数据
     *
     * @param dataList 数据列表
     * @param config   写入配置
     * @throws Exception
     */
    public void write(List<T> dataList, ExcelWriteConfig config) throws Exception {
        ExcelWriteConfigUtils.validateConfig(config);
        try {
            workbook = WorkbookCreator.createWorkbook(config);
            defaultCellStyleCreator = new DefaultCellStyleCreator(workbook);
            //默认样式初始化
            genericCellStyleManager = GenericCellStyleCreator.create(defaultCellStyleCreator, config.getGenericCellStyleHandler());
            //标题样式初始化
            titleCellStyleManager = new TitleCellStyleCreator().create(defaultCellStyleCreator, genericCellStyleManager, config.getTitleCellStyleHandler());
            //数据样式初始化
            dataCellStyleCreator = new DataCellStyleCreator();
            dataCellStyleManager = dataCellStyleCreator.create(defaultCellStyleCreator, genericCellStyleManager, config.getDataCellInitializeStyleHandler());
            //表达式初始化
            expressionManager.setTitleExpressionHandler(config.getTitleExpressionHandler());
            expressionManager.setDataList(dataList);
            List<WriteDataFieldDefinition> mainDataFields = ExcelWriteDataFieldDefinitionCreator.createDataFieldDefinitions(clazz, dataList.size() > 0 ? dataList.get(0) : null, config);
            List<WriteDataFieldDefinition> childDataFields = getChildrenDataFieldDefinitions(dataList, config);
            int maxTitleRowCount = calculateMaxTitleRowCount(mainDataFields, childDataFields);
            Sheet sheet = workbook.createSheet(config.getDefaultSheetName());
            int maxChildrenCount = createSheetTitle(dataList, mainDataFields, childDataFields, maxTitleRowCount, sheet);
            int maxRows = config.getExcelType().getConfig().getMaxRows() - maxTitleRowCount;
            int maxSheetCount = dataList.size() / maxRows + 1;
            AutoSizeColumnManager autoSizeColumnManager = new AutoSizeColumnManager();
            for (int i = 0; i < maxSheetCount; i++) {
                if (i > 0) {
                    sheet = workbook.createSheet(config.getDefaultSheetName() + i);
                    maxChildrenCount = createSheetTitle(dataList, mainDataFields, childDataFields, maxTitleRowCount, sheet);
                }
                int pageSize = Math.min(dataList.size() - i * maxRows, maxRows);
                for (int rowIndex = 0; rowIndex < pageSize; rowIndex++) {
                    Row row = sheet.createRow(rowIndex + maxTitleRowCount);
                    Object dataBase = dataList.get(i * pageSize + rowIndex);
                    for (int column = 0; column < mainDataFields.size(); column++) {
                        WriteDataFieldDefinition dataField = mainDataFields.get(column);
                        Cell cell = row.createCell(column);
                        Object originValue = null;
                        if (dataBase instanceof WriteDataBase) {
                            originValue = ((WriteDataBase) dataBase).getFieldValue(dataField.getKey());
                        } else {
                            originValue = dataField.getField().get(dataBase);
                        }
                        setCellValueStyle(dataBase, dataField, cell, originValue);
                        if (dataField.getStyleDefinition() != null && dataField.getStyleDefinition().isAutoSizeColumn()) {
                            autoSizeColumnManager.setMax(column, cell.toString().length());
                        }
                    }
                    writeChildren(mainDataFields, childDataFields, maxChildrenCount, autoSizeColumnManager, row, dataBase);
                }
                //自适应宽度
                autoSizeColumnManager.autoSizeColumn(sheet);
            }
            save(config);
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.close(workbook);
        }
    }

    /**
     * 获取子元素的字段定义
     *
     * @param dataList
     * @param config
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private List<WriteDataFieldDefinition> getChildrenDataFieldDefinitions(List<T> dataList, ExcelWriteConfig config) throws InstantiationException, IllegalAccessException {
        List<WriteDataFieldDefinition> childDataFields = null;
        if (dataList.size() > 0 && dataList.get(0) instanceof WriteDataBase) {
            WriteDataBase child = null;
            for (T item : dataList) {
                List<? extends WriteDataBase> writeDateChildren = ((WriteDataBase) item).getWriteDateChildren();
                if (writeDateChildren != null && writeDateChildren.size() > 0) {
                    child = writeDateChildren.get(0);
                    break;
                }
            }
            if (child != null) {
                childDataFields = ExcelWriteDataFieldDefinitionCreator.createDataFieldDefinitions(child.getClass(), child, config);
            }
        }
        if (childDataFields == null) {
            childDataFields = new ArrayList<>();
        }
        return childDataFields;
    }

    /**
     * 写入子元素
     *
     * @param mainDataFields
     * @param childDataFields
     * @param maxChildrenCount
     * @param autoSizeColumnManager
     * @param row
     * @param dataBase
     */
    private void writeChildren(List<WriteDataFieldDefinition> mainDataFields, List<WriteDataFieldDefinition> childDataFields, int maxChildrenCount, AutoSizeColumnManager autoSizeColumnManager, Row row, Object dataBase) {
        if (dataBase instanceof WriteDataBase) {
            for (int count = 0; count < maxChildrenCount; count++) {
                for (int column = 0; column < childDataFields.size(); column++) {
                    WriteDataFieldDefinition dataField = childDataFields.get(column);
                    int columnNum = column + mainDataFields.size() + count * childDataFields.size();
                    Cell cell = row.createCell(columnNum);
                    int size = ((WriteDataBase) dataBase).getWriteDateChildren() == null ? 0 : ((WriteDataBase) dataBase).getWriteDateChildren().size();
                    if (count < size) {
                        WriteDataBase subDataBase = ((WriteDataBase) dataBase).getWriteDateChildren().get(count);
                        Object originValue = null;
                        if (subDataBase != null) {
                            originValue = subDataBase.getFieldValue(dataField.getKey());
                        }
                        setCellValueStyle(subDataBase, dataField, cell, originValue);
                    } else {
                        cell.setCellStyle(genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.CONTENT.getType()));
                    }
                    if (dataField.getStyleDefinition() != null && dataField.getStyleDefinition().isAutoSizeColumn()) {
                        autoSizeColumnManager.setMax(columnNum, cell.toString().length());
                    }
                }
            }
        }
    }

    /**
     * 保存
     *
     * @param config
     * @throws Exception
     */
    private void save(ExcelWriteConfig config) throws Exception {
        if (config.getPassword() != null && !config.getPassword().trim().equals("")) {
            try (POIFSFileSystem poifsFileSystem = new POIFSFileSystem();
                 OutputStream stream = new FileOutputStream(config.getFilePath())
            ) {
                EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
                Encryptor enc = info.getEncryptor();
                enc.confirmPassword(config.getPassword());
                try (OutputStream encryptOutPutStream = enc.getDataStream(poifsFileSystem)) {
                    workbook.write(encryptOutPutStream);
                    encryptOutPutStream.flush();
                }
                poifsFileSystem.writeFilesystem(stream);
                stream.flush();
            }
        } else {
            try (OutputStream stream = new FileOutputStream(config.getFilePath())) {
                workbook.write(stream);
                stream.flush();
            }
        }
    }

    /**
     * 设置单元格值，样式
     *
     * @param dataBase
     * @param dataField
     * @param cell
     * @param originValue
     */
    private void setCellValueStyle(Object dataBase, WriteDataFieldDefinition dataField, Cell cell, Object originValue) {
        Object cellValue = null;
        if (dataField.getWriteValueConverter() != null) {
            cellValue = dataField.getWriteValueConverter().convert(new CellOriginData(cell, dataBase, originValue, originValue == null ? null : originValue.getClass()));
        } else {
            cellValue = originValue;
        }
        CellUtils.setCellValue(cell, cellValue);
        CellStyle cellStyle = null;
        ExcelStyleDefinition styleDefinition = dataField.getStyleDefinition();
        CellStyle defaultCellStyle = genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.CONTENT.getType());
        if (styleDefinition != null) {
            if (styleDefinition.getCellStyleHandler() != null) {
                cellStyle = styleDefinition.getCellStyleHandler().onCreating(new CellData(cell, dataBase, originValue, cellValue, defaultCellStyle, dataCellStyleManager, dataCellStyleCreator));
            } else {
                String dataFormatStr = styleDefinition.getDataFormat();
                if (dataFormatStr != null && !dataFormatStr.equals("")) {
                    cellStyle = generateDataFormatCellStyle(dataFormatStr, defaultCellStyle);
                }
            }
        }
        if (cellStyle == null) {
            if (cellValue != null && cellValue.getClass().isAssignableFrom(Date.class)) {
                cellStyle = generateDataFormatCellStyle("yyyy-MM-dd HH:mm:ss", defaultCellStyle);
            }
            if (cellStyle == null) {
                cellStyle = defaultCellStyle;
            }
        }
        cell.setCellStyle(cellStyle);
    }

    private CellStyle generateDataFormatCellStyle(String dataFormatStr, CellStyle defaultCellStyle) {
        CellStyle newCellStyle = dataCellStyleManager.getCellStyle(dataFormatStr);
        if (newCellStyle == null) {
            newCellStyle = defaultCellStyleCreator.newCellStyle();
            newCellStyle.cloneStyleFrom(defaultCellStyle);
            DataFormat dataFormat = defaultCellStyleCreator.newDataFormat();
            short formatIndex = dataFormat.getFormat(dataFormatStr);
            newCellStyle.setDataFormat(formatIndex);
            dataCellStyleManager.addCellStyle(dataFormatStr, newCellStyle);
        }
        return newCellStyle;
    }

    /**
     * 计算标题占用最大的行
     *
     * @param mainDataFields
     * @param childDataFields
     * @return
     */
    private int calculateMaxTitleRowCount(List<WriteDataFieldDefinition> mainDataFields, List<WriteDataFieldDefinition> childDataFields) {
        int maxTitleRowCount = 0;
        for (WriteDataFieldDefinition mainDataField : mainDataFields) {
            maxTitleRowCount = Math.max(mainDataField.getTitleNames() == null ? 0 : mainDataField.getTitleNames().size(), maxTitleRowCount);
        }
        for (WriteDataFieldDefinition childDataField : childDataFields) {
            maxTitleRowCount = Math.max(childDataField.getTitleNames() == null ? 0 : childDataField.getTitleNames().size(), maxTitleRowCount);
        }
        return maxTitleRowCount;
    }

    /**
     * 创建excel的表头
     *
     * @param dataList
     * @param mainDataFields
     * @param childDataFields
     * @param maxTitleRowCount
     * @param sheet
     * @return
     */
    private int createSheetTitle(List<?> dataList, List<WriteDataFieldDefinition> mainDataFields, List<WriteDataFieldDefinition> childDataFields, int maxTitleRowCount, Sheet sheet) {
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
                    Object parseName = expressionManager.parse(titleName);
                    if (parseName != null) {
                        titleName = parseName.toString();
                    }
                }
                cell.setCellValue(titleName);
                cell.setCellStyle(cellStyle);
            }
        }
        int maxChildrenCount = 0;
        if (childDataFields.size() > 0) {
            for (Object dataBase : dataList) {
                if (dataBase instanceof WriteDataBase) {
                    maxChildrenCount = Math.max(maxChildrenCount, ((WriteDataBase) dataBase).getWriteDateChildren() == null ? 0 : ((WriteDataBase) dataBase).getWriteDateChildren().size());
                }
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
        int lastColumn = mainDataFields.size() + maxChildrenCount * childDataFields.size() - 1;
        if (!(maxTitleRowCount - 1 < 0 || lastColumn < 0)) {
            ExcelMergeUtils.mergeRange(sheet, new CellRangeAddress(0, maxTitleRowCount - 1, 0, lastColumn));
        }
        return maxChildrenCount;
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
}
