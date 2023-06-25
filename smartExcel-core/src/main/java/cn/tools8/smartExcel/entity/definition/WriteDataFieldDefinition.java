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

    /**
     * 标题
     */
    private List<String> titleNames;
    /**
     * 关键字
     */
    private String key;
    /**
     * 排序 1最小
     */
    private Integer order;
    /**
     * 数值写入转换
     */
    private IWriteValueConverter  writeValueConverter;
    private Field field;
    /**
     * 列类型
     */
    private Class<?> valueType;
    //单元格样式定义
    private ExcelStyleDefinition styleDefinition;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getValueType() {
        return valueType;
    }

    public void setValueType(Class<?> valueType) {
        this.valueType = valueType;
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

    public IWriteValueConverter getWriteValueConverter() {
        return writeValueConverter;
    }

    public void setWriteValueConverter(IWriteValueConverter writeValueConverter) {
        this.writeValueConverter = writeValueConverter;
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
        this.valueType = other.getValueType();
        this.styleDefinition = other.getStyle();
    }
}
