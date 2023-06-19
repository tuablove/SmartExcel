package cn.tools8.smartExcel.entity;

import cn.tools8.smartExcel.handler.IReadValueConverter;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author tuaobin 2023/6/16$ 18:46$
 */
public class ImportField implements Serializable {
    private Field field;
    private IReadValueConverter converter;

    public ImportField() {
    }

    public ImportField(Field field, IReadValueConverter converter) {
        this.field = field;
        this.converter = converter;
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
}
