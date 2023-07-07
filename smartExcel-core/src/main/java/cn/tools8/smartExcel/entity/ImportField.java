package cn.tools8.smartExcel.entity;

import cn.tools8.smartExcel.handler.IReadValueConverter;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 导入字段
 * @author tuaobin 2023/6/16$ 18:46$
 */
public class ImportField implements Serializable {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段
     */
    private Field field;
    /**
     * 数据转换
     */
    private IReadValueConverter converter;
    /**
     * 列名称
     */
    private String columnName;
    public ImportField() {
    }

    public ImportField(String name, Field field, IReadValueConverter converter, String columnName) {
        this.name = name;
        this.field = field;
        this.converter = converter;
        this.columnName = columnName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public IReadValueConverter getConverter() {
        return converter;
    }

    public void setConverter(IReadValueConverter converter) {
        this.converter = converter;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
