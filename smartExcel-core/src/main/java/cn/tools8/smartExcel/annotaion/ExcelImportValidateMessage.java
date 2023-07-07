package cn.tools8.smartExcel.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记某字段为验证结果展示字段
 * 要求为List<String> 或  String 类型
 * @author tuaobin 2023/6/15$ 14:50$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelImportValidateMessage {

}
