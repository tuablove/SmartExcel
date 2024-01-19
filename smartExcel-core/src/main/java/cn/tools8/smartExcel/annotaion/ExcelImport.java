package cn.tools8.smartExcel.annotaion;

import cn.tools8.smartExcel.handler.IReadValueConverter;

import java.lang.annotation.*;

/**
 * 导入配置
 *
 * @author tuaobin 2023/6/15$ 14:50$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(value = ExcelImports.class)
public @interface ExcelImport {
    /**
     * 列名
     *
     * @return
     */
    String[] names() default {};

    /**
     * CellReference.convertColStringToIndex(sCol);
     */
    String columnString() default "";

    /**
     * 数据转换接口
     *
     * @return
     */
    Class<? extends IReadValueConverter> converter() default IReadValueConverter.class;

    /**
     * 分组
     * @return
     */
    Class<?>[] groups() default {};
}
