package cn.tools8.smartExcel.builder;

import cn.tools8.smartExcel.annotaion.ExcelExport;
import cn.tools8.smartExcel.annotaion.ExcelStyle;
import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.DynamicColumn;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.entity.definition.ExcelStyleDefinition;
import cn.tools8.smartExcel.entity.definition.WriteDataFieldDefinition;
import cn.tools8.smartExcel.exception.DataFieldRepeatError;
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
     * @param config
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static  List<WriteDataFieldDefinition> extractDataFields(Class<?> clazz, Object writeDataBase, ExcelWriteConfig config) throws InstantiationException, IllegalAccessException {
        Set<String> includeFields = config.getIncludeFields();
        Set<String> excludeFields = config.getExcludeFields();

        Set<String> keys = new HashSet<>();
        List<WriteDataFieldDefinition> dataFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ExcelExport excelExport = field.getAnnotation(ExcelExport.class);
            if (excelExport != null) {
                String fieldKey = field.getName();
                if (!isInclude(includeFields, excelExport.ignore(), fieldKey)) {
                    continue;
                }
                WriteDataFieldDefinition dataField = new WriteDataFieldDefinition();
                dataField.setKey(fieldKey);
                dataField.setOrder(excelExport.order());
                dataField.setTitleNames(Arrays.asList(excelExport.names()));
                if (!excelExport.converter().isInterface() && !Modifier.isAbstract(excelExport.converter().getModifiers())) {
                    IWriteValueConverter newInstance = excelExport.converter().newInstance();
                    dataField.setWriteValueConverter(newInstance);
                }
                field.setAccessible(true);
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
                    styleDefinition.setAutoSizeColumn(excelStyle.autoSizeColumn());
                    dataField.setStyleDefinition(styleDefinition);
                }
                keys.add(dataField.getKey());
                dataFields.add(dataField);
            }
        }
        if (writeDataBase instanceof WriteDataBase) {
            for (DynamicColumn dynamicColumn : (WriteDataBase) writeDataBase) {
                String fieldKey = dynamicColumn.getKey();
                if (keys.contains(fieldKey)) {
                    throw new DataFieldRepeatError(String.format("Class extends WriteDataBase 字段属性 %s重复", fieldKey));
                }
                if (!isInclude(includeFields, dynamicColumn.isIgnore(), fieldKey)) {
                    continue;
                }
                WriteDataFieldDefinition dataField = new WriteDataFieldDefinition();
                dataField.copyFrom(dynamicColumn);
                if (dynamicColumn.getOrder() == null) {
                    dataField.setOrder(dataFields.stream().map(WriteDataFieldDefinition::getOrder).max(Integer::compare).get() + 1);
                }
                if (dynamicColumn.getWriteValueConverter() != null && !dynamicColumn.getWriteValueConverter().isInterface() && !Modifier.isAbstract(dynamicColumn.getWriteValueConverter().getModifiers())) {
                    dataField.setWriteValueConverter(dynamicColumn.getWriteValueConverter().newInstance());
                }
                dataFields.add(dataField);
            }
        }
        dataFields.sort(Comparator.comparing(WriteDataFieldDefinition::getOrder));
        //移除不要的字段
        if (excludeFields != null && excludeFields.size() > 0) {
            dataFields.removeIf(item -> excludeFields.contains(item.getKey()));
        }
        return dataFields;
    }

    /**
     * 是否包含该字段
     *
     * @param includeFields     包含的字段
     * @param fieldIgnoreConfig
     * @param fieldKey
     * @return
     */
    private static boolean isInclude(Set<String> includeFields, boolean fieldIgnoreConfig, String fieldKey) {
        boolean exist;
        if (includeFields != null && includeFields.size() > 0) {
            exist = includeFields.contains(fieldKey);
        } else {
            exist = !fieldIgnoreConfig;
        }
        return exist;
    }
}
