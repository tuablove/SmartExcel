package cn.tools8.smartExcel;

import cn.tools8.smartExcel.builder.ExcelWriteDataFieldDefinitionCreator;
import cn.tools8.smartExcel.builder.GenericCellStyleCreator;
import cn.tools8.smartExcel.config.ExcelWriteBaseConfig;
import cn.tools8.smartExcel.config.ExcelWriteTemplateConfig;
import cn.tools8.smartExcel.entity.CellOriginData;
import cn.tools8.smartExcel.entity.CellData;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.entity.definition.ExcelStyleDefinition;
import cn.tools8.smartExcel.entity.definition.WriteDataFieldDefinition;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.manager.AutoSizeColumnManager;
import cn.tools8.smartExcel.manager.ExcelWriteCellStyleManager;
import cn.tools8.smartExcel.manager.ExpressionManager;
import cn.tools8.smartExcel.utils.CellUtils;
import cn.tools8.smartExcel.utils.ExcelWriteConfigUtils;
import cn.tools8.smartExcel.utils.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * excel写入类
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelWriterTemplateSimple<T> extends AbstractExcel implements IExcelCellStyleCreator {
    private static final Logger logger = LoggerFactory.getLogger(ExcelWriterTemplateSimple.class);
    private ExcelWriteCellStyleManager genericCellStyleManager;
    private final ExcelWriteCellStyleManager dataCellStyleManager = new ExcelWriteCellStyleManager();
    private final ExpressionManager expressionManager = new ExpressionManager<T>();

    /**
     * 写入数据
     *
     * @param dataList 数据列表
     * @param config   写入配置
     * @throws Exception
     */
    public void write(List<T> dataList, ExcelWriteTemplateConfig config) throws Exception {
        ExcelWriteConfigUtils.validateConfig(config);
        try (FileInputStream fileIs = new FileInputStream(config.getTemplateFilePath());) {
            workbook = WorkbookFactory.create(fileIs, config.getPassword());
            //默认样式初始化
            genericCellStyleManager = GenericCellStyleCreator.create(this, config.getGenericCellStyleHandler());
            expressionManager.setDataList(dataList);
            List<WriteDataFieldDefinition> dataFields = ExcelWriteDataFieldDefinitionCreator.createDataFieldDefinitions(clazz, dataList.size() > 0 ? dataList.get(0) : null, config);
            Map<Integer, WriteDataFieldDefinition> dataFieldsMap = dataFields.stream().collect(Collectors.toMap(WriteDataFieldDefinition::getOrder, item -> item));
            Sheet sheet = workbook.getSheetAt(0);
            int rowIndex = config.getDataBeginRowIndex();
            if (rowIndex < sheet.getLastRowNum()) {
                sheet.shiftRows(rowIndex + 1, sheet.getLastRowNum(), dataList.size() - 1);
            }
            Map<Integer, CellStyle> cellStyleMap = new HashMap<>();
            AutoSizeColumnManager autoSizeColumnManager = new AutoSizeColumnManager();
            short lastCellNum = 0;
            for (int i = 0; i < dataList.size(); i++, rowIndex++) {
                T dataBase = dataList.get(i);
                Row row = sheet.getRow(rowIndex);
                if (i == 0 && row != null) {
                    lastCellNum = row.getLastCellNum();
                    for (int c = 0; c < lastCellNum; c++) {
                        if (ObjectUtils.isNotEmpty(row.getCell(c))) {
                            cellStyleMap.put(c, row.getCell(c).getCellStyle());   
                        }
                    }
                }
                if (row == null) {
                    row = sheet.createRow(rowIndex);
                }
                for (int c = 0; c < lastCellNum; c++) {
                    Cell cell = row.getCell(c);
                    if (cell == null) {
                        cell = row.createCell(c);
                    }
                    if (i > 0) {
                        cell.setCellStyle(cellStyleMap.get(c));
                    }
                    WriteDataFieldDefinition dataField = dataFieldsMap.get(c);
                    if (dataField != null) {
                        Object originValue = null;
                        if (dataBase instanceof WriteDataBase) {
                            originValue = ((WriteDataBase) dataBase).getFieldValue(dataField.getKey());
                        } else {
                            originValue = dataField.getField().get(dataBase);
                        }
                        setCellValueStyle(dataBase, dataField, cell, originValue, cell.getCellStyle());
                        if (dataField.getStyleDefinition() != null && dataField.getStyleDefinition().isAutoSizeColumn()) {
                            autoSizeColumnManager.setMax(dataField.getOrder(), cell.toString().length());
                        }
                    }
                }
            }
            //自适应宽度
            autoSizeColumnManager.autoSizeColumn(sheet);
            save(config);
        } catch (
                Exception e) {
            throw e;
        } finally {
            IOUtils.close(workbook);
        }

    }

    /**
     * 保存
     *
     * @param config
     * @throws Exception
     */
    private void save(ExcelWriteBaseConfig config) throws Exception {
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
    private void setCellValueStyle(Object dataBase, WriteDataFieldDefinition dataField, Cell cell, Object originValue, CellStyle defaultCellStyle) {
        Object cellValue = null;
        if (dataField.getWriteValueConverter() != null) {
            cellValue = dataField.getWriteValueConverter().convert(new CellOriginData(cell, dataBase, originValue, originValue == null ? null : originValue.getClass()));
        } else {
            cellValue = originValue;
        }
        CellUtils.setCellValue(cell, cellValue);
        CellStyle cellStyle = defaultCellStyle;
        ExcelStyleDefinition styleDefinition = dataField.getStyleDefinition();
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
     * 目标类
     */
    private Class<T> clazz;

    /**
     * 初始化
     *
     * @param clazz
     */
    public ExcelWriterTemplateSimple(Class<T> clazz) {
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
