package cn.tools8.smartExcel.entity;

import cn.tools8.smartExcel.entity.definition.ExcelStyleDefinition;
import cn.tools8.smartExcel.handler.IWriteValueConverter;

import java.io.Serializable;
import java.util.List;

/**
 * 动态列
 * @author tuaobin 2023/6/19$ 15:44$
 */
public class DynamicColumn implements Serializable {
    /**
     * 表头标题
     */
    private List<String> titleNames;
    /**
     * 唯一标识符
     */
    private String key;
    /**
     * 顺序 升序
     */
    private Integer order;
    /**
     * 数据值
     */
    private Object value;
    /**
     * 数据转换处理类型
     */
    private Class<? extends IWriteValueConverter>  writeValueConverter;
    /**
     * 单元格样式
     */
    private ExcelStyleDefinition style;

    public DynamicColumn() {
    }

    public List<String> getTitleNames() {
        return titleNames;
    }

    public void setTitleNames(List<String> titleNames) {
        this.titleNames = titleNames;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class<? extends IWriteValueConverter> getWriteValueConverter() {
        return writeValueConverter;
    }

    public void setWriteValueConverter(Class<? extends IWriteValueConverter> writeValueConverter) {
        this.writeValueConverter = writeValueConverter;
    }

    public ExcelStyleDefinition getStyle() {
        return style;
    }

    public void setStyle(ExcelStyleDefinition style) {
        this.style = style;
    }
}
