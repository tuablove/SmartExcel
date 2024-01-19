package cn.tools8.smartExcel.annotaion;

import cn.tools8.smartExcel.enums.ExcelMergeTypeEnum;
import cn.tools8.smartExcel.handler.IWriteDataCellStyleHandler;

import java.lang.annotation.*;

/**
 * 样式
 *
 * @author tuaobin 2023/6/15$ 14:50$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelStyles {
    ExcelStyle[] value();
}
