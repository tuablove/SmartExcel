package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.entity.SortOrderColumn;

import java.util.List;

/**
 * 动态排序
 */
public interface IWriteSortOrderHandler {
    /**
     * 排序
     * @param clazz
     * @param columns
     * @return
     */
    void sort(Class<?> clazz, List<SortOrderColumn> columns);
}
