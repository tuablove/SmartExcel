package cn.tools8.smartExcel.annotaion;

import cn.tools8.smartExcel.handler.IWriteDataCellStyleHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 样式
 *
 * @author tuaobin 2023/6/15$ 14:50$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelStyle {
    /**
     * 数据格式样式
     * 时间类型默认:yyyy-MM-dd HH:mm:ss
     * 数值类型默认:#,##0.000
     *
     * @return
     */
    String dataFormat() default "";

    /**
     * 给单元格设置新的样式
     *
     * @return
     */
    Class<? extends IWriteDataCellStyleHandler> cellStyleHandler() default IWriteDataCellStyleHandler.class;

    /**
     * 宽度自适应
     * @return
     */
    boolean autoSizeColumn() default false;
}
