package cn.tools8.smartExcel.builder;

import cn.tools8.smartExcel.annotaion.ExcelExport;
import cn.tools8.smartExcel.annotaion.ExcelStyle;
import cn.tools8.smartExcel.entity.DynamicColumn;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.entity.definition.ExcelStyleDefinition;
import cn.tools8.smartExcel.entity.definition.WriteDataFieldDefinition;
import cn.tools8.smartExcel.handler.IWriteDataCellStyleHandler;
import cn.tools8.smartExcel.handler.IWriteValueConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 数据字段定义创建
 *
 * @author tuaobin 2023/6/25$ 10:25$
 */
public class ExcelWriteDataFieldDefinitionCreator {
    /**
     * @param clazz
     * @param writeDataBase
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static List<WriteDataFieldDefinition> extractDataFields(Class<? extends WriteDataBase> clazz, WriteDataBase writeDataBase) throws InstantiationException, IllegalAccessException {
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
                    IWriteValueConverter newInstance = excelExport.converter().newInstance();
                    dataField.setWriteValueConverter(newInstance);
                }
                dataField.setField(field);
                dataField.setValueType(field.getType());
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
                if (dynamicColumn.getWriteValueConverter() != null && !dynamicColumn.getWriteValueConverter().isInterface() && !Modifier.isAbstract(dynamicColumn.getWriteValueConverter().getModifiers())) {
                    dataField.setWriteValueConverter(dynamicColumn.getWriteValueConverter().newInstance());
                }
                dataFields.add(dataField);
            }
        }
        dataFields.sort(Comparator.comparing(WriteDataFieldDefinition::getOrder));
        return dataFields;
    }
}
