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
public @interface ExcelExports {
    ExcelExport[] value();
}
