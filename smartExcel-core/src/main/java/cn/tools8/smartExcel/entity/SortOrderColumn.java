package cn.tools8.smartExcel.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 排序处理
 *
 * @author tuaobin 2023/6/20$ 10:24$
 */
public class SortOrderColumn implements Serializable {

    public SortOrderColumn() {
    }

    public SortOrderColumn(List<String> titleNames, String key, Integer order) {
        this.titleNames = titleNames;
        this.key = key;
        this.order = order;
    }

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

    public List<String> getTitleNames() {
        return titleNames;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
