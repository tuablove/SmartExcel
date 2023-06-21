package cn.tools8.smartExcel.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tuaobin 2023/6/19$ 15:43$
 */
public class WriteDataBase extends ArrayList<DynamicColumn> implements Serializable {
    private Map<String, DynamicColumn> dynamicColumnMap = null;

    private List<? extends WriteDataBase> writeDateChildren;

    public List<? extends WriteDataBase> getWriteDateChildren() {
        return writeDateChildren;
    }

    public void setWriteDateChildren(List<? extends WriteDataBase> writeDateChildren) {
        this.writeDateChildren = writeDateChildren;
    }

    public Object getFieldValue(String key) {
        if (key == null || key.equals("")) {
            return null;
        }
        try {
            Field field = this.getClass().getDeclaredField(key);
            field.setAccessible(true);
            return field.get(this);
        } catch (NoSuchFieldException ignore) {
            return getDynamicColumnValue(key);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return null;
    }

    public Object getDynamicColumnValue(String key) {
        DynamicColumn dynamicColumn = getDynamicColumn(key);
        if (dynamicColumn != null) {
            return dynamicColumn.getValue();
        }
        return null;
    }

    public DynamicColumn getDynamicColumn(String key) {
        if (this.size() > 0) {
            if (dynamicColumnMap == null) {
                dynamicColumnMap = this.stream().collect(Collectors.toMap(DynamicColumn::getKey, item -> item));
            }
            return dynamicColumnMap.get(key);
        }
        return null;
    }
}
