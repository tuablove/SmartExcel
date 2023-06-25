package cn.tools8.smartExcel;

import cn.tools8.smartExcel.builder.ExcelWriteDataFieldDefinitionCreator;
import cn.tools8.smartExcel.builder.GenericCellStyleCreator;
import cn.tools8.smartExcel.builder.TitleCellStyleCreator;
import cn.tools8.smartExcel.builder.WorkbookCreator;
import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.CellData;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.entity.definition.ExcelStyleDefinition;
import cn.tools8.smartExcel.entity.definition.WriteDataFieldDefinition;
import cn.tools8.smartExcel.enums.GenericStyleTypeEnum;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.manager.ExcelWriteCellStyleManager;
import cn.tools8.smartExcel.manager.ExpressionManager;
import cn.tools8.smartExcel.utils.CellUtils;
import cn.tools8.smartExcel.utils.ExcelMergeUtils;
import cn.tools8.smartExcel.utils.IOUtils;
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
public class ExcelWriter<T extends WriteDataBase> extends AbstractExcel implements IExcelCellStyleCreator {
    private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
    private ExcelWriteCellStyleManager genericCellStyleManager;
    private ExcelWriteCellStyleManager titleCellStyleManager;
    private final ExcelWriteCellStyleManager dataCellStyleManager = new ExcelWriteCellStyleManager();
    private final ExpressionManager expressionManager = new ExpressionManager();

    public void write(List<? extends WriteDataBase> dataList, ExcelWriteConfig config) throws Exception {
        try {
            workbook = WorkbookCreator.createWorkbook(config);
            //默认样式初始化
            genericCellStyleManager = GenericCellStyleCreator.create(this, config.getGenericCellStyleHandler());
            //标题样式初始化
            titleCellStyleManager = TitleCellStyleCreator.create(this, config.getTitleCellStyleHandler());
            //表达式初始化
            expressionManager.setTitleExpressionHandler(config.getTitleExpressionHandler());

            List<WriteDataFieldDefinition> mainDataFields = ExcelWriteDataFieldDefinitionCreator.extractDataFields(clazz, dataList.size() > 0 ? dataList.get(0) : null);
            List<WriteDataFieldDefinition> childDataFields = null;
            if (dataList.size() > 0) {
                List<? extends WriteDataBase> writeDateChildren = dataList.get(0).getWriteDateChildren();
                childDataFields = ExcelWriteDataFieldDefinitionCreator.extractDataFields(writeDateChildren.get(0).getClass(), writeDateChildren.get(0));
            } else {
                childDataFields = new ArrayList<>();
            }
            int maxTitleRowCount = calculateMaxTitleRowCount(mainDataFields, childDataFields);
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
                        Object originValue = dataBase.getFieldValue(dataField.getKey());
                        setCellValueStyle(dataBase, dataField, cell, originValue);
                    }
                    if (dataBase.getWriteDateChildren() != null && dataBase.getWriteDateChildren().size() > 0) {
                        for (int count = 0; count < dataBase.getWriteDateChildren().size(); count++) {
                            for (int column = 0; column < childDataFields.size(); column++) {
                                WriteDataFieldDefinition dataField = childDataFields.get(column);
                                Cell cell = row.createCell(column + mainDataFields.size() + count * childDataFields.size());
                                WriteDataBase subDataBase = dataBase.getWriteDateChildren().get(count);
                                Object originValue = subDataBase.getFieldValue(dataField.getKey());
                                setCellValueStyle(subDataBase, dataField, cell, originValue);
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

    /**
     * 设置单元格值，样式
     *
     * @param dataBase
     * @param dataField
     * @param cell
     * @param originValue
     */
    private void setCellValueStyle(WriteDataBase dataBase, WriteDataFieldDefinition dataField, Cell cell, Object originValue) {
        Object cellValue = null;
        if (dataField.getWriteValueConverter() != null) {
            cellValue = dataField.getWriteValueConverter().convert(cell, originValue, originValue.getClass());
        } else {
            cellValue = originValue;
        }
        CellUtils.setCellValue(cell, cellValue);
        CellStyle cellStyle = null;
        ExcelStyleDefinition styleDefinition = dataField.getStyleDefinition();
        CellStyle defaultCellStyle = genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.CONTENT.getType());
        if (styleDefinition != null) {
            if (styleDefinition.getCellStyleHandler() != null) {
                cellStyle = styleDefinition.getCellStyleHandler().onCreating(new CellData(cell, dataBase, originValue, cellValue, defaultCellStyle, dataCellStyleManager, this));
            } else {
                String dataFormatStr = styleDefinition.getDataFormat();
                if (dataFormatStr != null && !dataFormatStr.equals("")) {
                    cellStyle = generateDataFormatCellStyle(dataFormatStr, defaultCellStyle);
                }
            }
        }
        if (cellStyle == null) {
            if (cellValue.getClass().isAssignableFrom(Date.class)) {
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
            newCellStyle = newCellStyle();
            newCellStyle.cloneStyleFrom(defaultCellStyle);
            DataFormat dataFormat = newDataFormat();
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
     */
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
        int lastColumn = mainDataFields.size() + maxChildrenCount * childDataFields.size() - 1;
        ExcelMergeUtils.mergeRange(sheet, new CellRangeAddress(0, maxTitleRowCount - 1, 0, lastColumn));
//        for (int i = 0; i <= lastColumn; i++) {
//            sheet.autoSizeColumn(0, false);
//        }
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
