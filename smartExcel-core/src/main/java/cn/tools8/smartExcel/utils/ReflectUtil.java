package cn.tools8.smartExcel.utils;

import org.apache.commons.compress.utils.Lists;
import org.apache.poi.util.ArrayUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射
 */
public class ReflectUtil {
    /**
     * 查找特定注解
     * @param field
     * @param annotationClass
     * @param groups
     * @return
     * @param <T>
     */
    public static <T extends Annotation> T getAnnotationWithGroups(Field field, Class<T> annotationClass, Class<?>[] groups) {
        T[] annotations = field.getAnnotationsByType(annotationClass);
        if (annotations.length > 0) {
            if (annotations.length == 1 || groups == null || groups.length == 0) {
                return annotations[0];
            } else {
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
                } catch (NoSuchMethodException e) {
                    return annotations[0];
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            return field.getAnnotation(annotationClass);
        }
        return null;
    }
}
