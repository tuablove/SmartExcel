package cn.tools8.smartExcel.config;

import cn.tools8.smartExcel.handler.IWriteSortOrderHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 写入excel配置
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelWriteFieldConfig extends  ExcelWriteBaseConfig {

    /**
     * 设置包含的字段
     */
    private Set<String> includeFields;
    /**
     * 设置不包含的字段,不包含字段最后处理
     * 如果包含字段设置了，不包含字段列表也设置了，最终结果未不包含
     */
    private Set<String> excludeFields;

    /**
     * 重新排序
     */
    private IWriteSortOrderHandler sortOrderHandler;

    public Set<String> getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(Set<String> includeFields) {
        this.includeFields = includeFields;
    }

    public Set<String> getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(Set<String> excludeFields) {
        this.excludeFields = excludeFields;
    }

    public void setExcludeFields(String... excludeFields) {
        this.excludeFields = new HashSet<String>(Arrays.asList(excludeFields));
    }

    public void setIncludeFields(String... includeFields) {
        this.includeFields = new HashSet<String>(Arrays.asList(includeFields));
    }

    public IWriteSortOrderHandler getSortOrderHandler() {
        return sortOrderHandler;
    }

    public void setSortOrderHandler(IWriteSortOrderHandler orderHandler) {
        this.sortOrderHandler = orderHandler;
    }
}
