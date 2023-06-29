package cn.tools8.smartExcel.annotaion;

import cn.tools8.smartExcel.handler.IWriteValueConverter;

import java.lang.annotation.*;

/**
 * 导出配置
 * @author tuaobin 2023/6/15$ 14:50$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelExport {
    /**
     * 列名
     * @return
     */
    String[] names() default {};

    /**
     * 顺序
     * @return
     */
    int order() default 0;

    /**
     * 数据转换接口
     * @return
     */
    Class<? extends IWriteValueConverter> converter() default IWriteValueConverter.class;

    /**
     * 是否忽略 true=忽略 false=不忽略
     * @return
     */
    boolean ignore() default false;
}
