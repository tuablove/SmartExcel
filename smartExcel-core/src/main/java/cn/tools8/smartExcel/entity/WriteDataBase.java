package cn.tools8.smartExcel.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据基类
 *
 * @author tuaobin 2023/6/19$ 15:43$
 */
public class WriteDataBase extends ArrayList<DynamicColumn> implements Serializable {
    private volatile Map<String, DynamicColumn> dynamicColumnMap = null;
    /**
     * 子对象
     */
    private List<? extends WriteDataBase> writeDateChildren;

    public List<? extends WriteDataBase> getWriteDateChildren() {
        return writeDateChildren;
    }

    public void setWriteDateChildren(List<? extends WriteDataBase> writeDateChildren) {
        this.writeDateChildren = writeDateChildren;
    }

    @Override
    public boolean add(DynamicColumn dynamicColumn) {
        if (dynamicColumn == null) {
            throw new IllegalArgumentException("dynamicColumn is null");
        }
        if (dynamicColumn.getKey() == null || dynamicColumn.getKey().trim().equals("")) {
            throw new IllegalArgumentException("dynamicColumn key is null");
        }
        synchronized (this) {
            if (exist(dynamicColumn.getKey())) {
                throw new IllegalArgumentException("dynamicColumn key is repeat");
            }
            boolean success = super.add(dynamicColumn);
            if (success) {
                dynamicColumnMap = null;
            }
            return success;
        }
    }

    /**
     * 获取所有属性的值(包含动态列)
     *
     * @param key
     * @return
     */
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
        }
        return null;
    }

    /**
     * '
     * 是否存在指定的field或key
     *
     * @param key
     * @return
     */
    private boolean exist(String key) {
        try {
            this.getClass().getDeclaredField(key);
            return true;
        } catch (NoSuchFieldException ignore) {
            return getDynamicColumn(key) != null;
        } catch (Exception ignore) {
        }
        return false;
    }

    /**
     * 获取动态列的值
     *
     * @param key
     * @return
     */
    public Object getDynamicColumnValue(String key) {
        DynamicColumn dynamicColumn = getDynamicColumn(key);
        if (dynamicColumn != null) {
            return dynamicColumn.getValue();
        }
        return null;
    }

    /**
     * 获取指定的动态列
     *
     * @param key
     * @return
     */
    public DynamicColumn getDynamicColumn(String key) {
        if (this.size() > 0) {
            if (dynamicColumnMap == null) {
                synchronized (this) {
                    if (dynamicColumnMap == null) {
                        dynamicColumnMap = this.stream().collect(Collectors.toMap(DynamicColumn::getKey, item -> item));
                    }
                }
            }
            return dynamicColumnMap.get(key);
        }
        return null;
    }

    /**
     * 设置动态列的值
     *
     * @param key
     * @param value
     */
    public void setDynamicColumnValue(String key, Object value) {
        DynamicColumn dynamicColumn = getDynamicColumn(key);
        if (dynamicColumn != null) {
            dynamicColumn.setValue(value);
        }
    }

    /**
     * 拷贝动态列到新对象
     *
     * @param base
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void cloneDynamicColumnTo(WriteDataBase base) throws InstantiationException, IllegalAccessException {
        if (this.size() > 0) {
            for (DynamicColumn dynamicColumn : this) {
                DynamicColumn column = dynamicColumn.deepClone();
                column.setValue(null);
                base.add(column);
            }
        }

    }

    @Override
    public String toString() {
        return "WriteDataBase{" +
                "dynamicColumnMap=" + dynamicColumnMap +
                ", writeDateChildren=" + writeDateChildren +
                ", modCount=" + modCount +
                "} " + super.toString();
    }
}
