package cn.tools8;

import cn.tools8.smartExcel.annotaion.ExcelImport;
import cn.tools8.smartExcel.utils.ReflectUtil;
import cn.tools8.smartExcelDemo.entity.GradeFeeReadDto;
import cn.tools8.smartExcelDemo.entity.StudentScoreDto;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectUtilTest {
    public static void main(String[] args) {
        Field[] declaredFields = GradeFeeReadDto.class.getDeclaredFields();
//        Field[] declaredFields = StudentScoreDto.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ExcelImport excelImport = ReflectUtil.getAnnotationWithGroups(declaredField, ExcelImport.class, null);
            if(excelImport!=null) {
                System.out.println(String.join(";", excelImport.names()) + "  " + Arrays.stream(excelImport.groups()).map(Class::getCanonicalName).collect(Collectors.joining(";")));
            }
        }
        System.out.println("=============================");
        for (Field declaredField : declaredFields) {
            ExcelImport excelImport = ReflectUtil.getAnnotationWithGroups(declaredField, ExcelImport.class, new Class[]{Integer.class});
            if (excelImport != null) {
                System.out.println(String.join(";", excelImport.names()) + "  " + Arrays.stream(excelImport.groups()).map(Class::getCanonicalName).collect(Collectors.joining(";")));
            }
        }
        System.out.println("=============================");
        for (Field declaredField : declaredFields) {
            ExcelImport excelImport = ReflectUtil.getAnnotationWithGroups(declaredField, ExcelImport.class, new Class[]{Long.class});
            if (excelImport != null) {
                System.out.println(String.join(";", excelImport.names()) + "  " + Arrays.stream(excelImport.groups()).map(Class::getCanonicalName).collect(Collectors.joining(";")));
            }
        }
        System.out.println("=============================");
        for (Field declaredField : declaredFields) {
            ExcelImport excelImport = ReflectUtil.getAnnotationWithGroups(declaredField, ExcelImport.class, new Class[]{GradeFeeReadDto.class});
            if (excelImport != null) {
                System.out.println(String.join(";", excelImport.names()) + "  " + Arrays.stream(excelImport.groups()).map(Class::getCanonicalName).collect(Collectors.joining(";")));
            }
        }
        System.out.println("=============================");
        for (Field declaredField : declaredFields) {
            ExcelImport excelImport = ReflectUtil.getAnnotationWithGroups(declaredField, ExcelImport.class, new Class[]{String.class});
            if (excelImport != null) {
                System.out.println(String.join(";", excelImport.names()) + "  " + Arrays.stream(excelImport.groups()).map(Class::getCanonicalName).collect(Collectors.joining(";")));
            }
        }
        System.out.println("=============================");
    }
}
