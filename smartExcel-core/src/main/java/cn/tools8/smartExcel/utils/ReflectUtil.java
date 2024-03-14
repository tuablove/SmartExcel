package cn.tools8.smartExcel.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 反射
 */
public class ReflectUtil {
    /**
     * 查找特定注解
     *
     * @param field
     * @param annotationClass
     * @param groups
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnnotationWithGroups(Field field, Class<T> annotationClass, Class<?>[] groups) {
        //获取多行形式
        T[] annotations = field.getAnnotationsByType(annotationClass);
        if (annotations.length > 0) {
            //获取多行
            if (groups != null && groups.length > 0) {
                return getAnnotationWithGroups(annotationClass, groups, Arrays.asList(annotations));
            } else {
                return annotations[0];
            }
        } else {
            //多行没有，获取单行
            T annotation = field.getAnnotation(annotationClass);
            if (annotation != null && groups != null && groups.length > 0) {
                return getAnnotationWithGroups(annotationClass, groups, Collections.singletonList(annotation));
            }
            return annotation;
        }
    }

    /**
     * 获取符合groups的注解
     *
     * @param annotationClass
     * @param groups
     * @param annotations
     * @param <T>
     * @return
     */
    private static <T extends Annotation> T getAnnotationWithGroups(Class<T> annotationClass, Class<?>[] groups, List<T> annotations) {
        try {
            List<Class<?>> groupsList = Arrays.asList(groups);
            Method groupsMethod = annotationClass.getDeclaredMethod("groups");
            groupsMethod.setAccessible(true);
            for (T annotation : annotations) {
                Object groupValue = groupsMethod.invoke(annotation);
                if (groupValue != null) {
                    Class<?>[] groupClazz = (Class<?>[]) groupValue;
                    if (Arrays.stream(groupClazz).anyMatch(groupsList::contains)) {
                        return annotation;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
