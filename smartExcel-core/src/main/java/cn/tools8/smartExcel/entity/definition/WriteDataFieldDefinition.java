package cn.tools8.smartExcel.entity.definition;

import cn.tools8.smartExcel.entity.DynamicColumn;
import cn.tools8.smartExcel.handler.IWriteValueConverter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字段定义
 *
 * @author tuaobin 2023/6/20$ 10:24$
 */
public class WriteDataFieldDefinition implements Serializable {


    private List<String> titleNames;
    private String key;
    private Integer order;
    private Object value;
    private Class<? extends IWriteValueConverter> writeValueConverter;
    private IWriteValueConverter writeValueConverterInstance;
    private Field field;
    private Class<?> fieldType;
    //单元格样式定义
    private ExcelStyleDefinition styleDefinition;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public List<String> getTitleNames() {
        return titleNames;
    }

    public void setTitleNames(List<String> titleNames) {
        this.titleNames = titleNames;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class<? extends IWriteValueConverter> getWriteValueConverter() {
        return writeValueConverter;
    }

    public void setWriteValueConverter(Class<? extends IWriteValueConverter> writeValueConverter) {
        this.writeValueConverter = writeValueConverter;
    }

    public IWriteValueConverter getWriteValueConverterInstance() {
        return writeValueConverterInstance;
    }

    public void setWriteValueConverterInstance(IWriteValueConverter writeValueConverterInstance) {
        this.writeValueConverterInstance = writeValueConverterInstance;
    }

    public ExcelStyleDefinition getStyleDefinition() {
        return styleDefinition;
    }

    public void setStyleDefinition(ExcelStyleDefinition styleDefinition) {
        this.styleDefinition = styleDefinition;
    }

    public void copyFrom(DynamicColumn other) {
        this.titleNames = new ArrayList<>(other.getTitleNames());
        this.key = other.getKey();
        this.order = other.getOrder();
        this.value = other.getValue();
        this.writeValueConverter = other.getWriteValueConverter();
        this.styleDefinition = other.getStyle();
    }
}
