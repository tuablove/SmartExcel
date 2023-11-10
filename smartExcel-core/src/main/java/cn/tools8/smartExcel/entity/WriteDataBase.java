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

    public WriteDataBase() {

    }

    public WriteDataBase(Collection<? extends DynamicColumn> c) {
        this.addAll(c);
    }

    /**
     * 添加
     *
     * @param c
     * @return
     */
    public boolean addMulti(DynamicColumn... c) {
        synchronized (this) {
            this.addAll(Arrays.asList(c));
            return true;
        }
    }

    @Override
    public boolean addAll(Collection<? extends DynamicColumn> c) {
        synchronized (this) {
            for (DynamicColumn dynamicColumn : c) {
                this.add(dynamicColumn);
            }
            return true;
        }
    }

    @Override
    public void add(int index, DynamicColumn element) {
        synchronized (this) {
            int lastIndex = this.size();
            add(element);
            DynamicColumn old = this.set(index, element);
            this.set(lastIndex, old);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends DynamicColumn> c) {
        synchronized (this) {
            int i = 0;
            for (DynamicColumn dynamicColumn : c) {
                add(index + i, dynamicColumn);
                i += 1;
            }
            return true;
        }
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
     * 是否存在指定的field或key
     *
     * @param key
     * @return
     */
    private boolean exist(String key) {
        DynamicColumn dynamicColumn = getDynamicColumn(key);
        if (dynamicColumn != null) {
            return true;
        } else {
            try {
                this.getClass().getDeclaredField(key);
                return true;
            } catch (NoSuchFieldException ignore) {
                return false;
            } catch (Exception ignore) {
            }
            return false;
        }
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
        if (base != null && this.size() > 0) {
            for (DynamicColumn dynamicColumn : this) {
                DynamicColumn column = dynamicColumn.deepClone();
                column.setValue(null);
                base.add(column);
            }
        }

    }

    /**
     * 从别的对象拷贝动态对象到当前对象
     *
     * @param source
     */
    public void cloneDynamicColumnFrom(WriteDataBase source) {
        if (source != null && source.size() > 0) {
            for (DynamicColumn dynamicColumn : source) {
                DynamicColumn column = dynamicColumn.deepClone();
                column.setValue(null);
                this.add(column);
            }
        }
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        DynamicColumn dynamicColumn = getDynamicColumn(key);
        if (dynamicColumn != null) {
            dynamicColumn.setValue(value);
        } else {
            try {
                Field declaredField = this.getClass().getDeclaredField(key);
                declaredField.setAccessible(true);
                declaredField.set(this, value);
            } catch (Exception ignore) {

            }
        }
    }

    /**
     * 直接导入map值
     *
     * @param map
     */
    public void set(Map<String, Object> map) {
        if (map != null) {
            for (Map.Entry<String, Object> objectEntry : map.entrySet()) {
                set(objectEntry.getKey(), objectEntry.getValue());
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
