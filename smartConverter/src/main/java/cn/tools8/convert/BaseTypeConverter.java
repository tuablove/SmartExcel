package cn.tools8.convert;

import cn.tools8.convert.exception.UnsupportedClassTypeError;

import java.math.BigDecimal;

/**
 * @author tuaobin 2023/6/16$ 09:49$
 */
public class BaseTypeConverter {

    /**
     * Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE
     *
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convert(Object object, Class<T> clazz) {
        return convert(object, clazz, null);
    }

    /**
     * 转换，失败或空值时返回默认值
     *
     * @param object       源对象
     * @param clazz        目标类型
     * @param defaultValue 默认值
     * @param <T>          泛型
     * @return 目标值
     */
    public static <T> T convert(Object object, Class<T> clazz, T defaultValue) {
        if (clazz == null) {
            return defaultValue;
        }
        if (object == null) {
            return defaultValue;
        }
        Class<?> sourceClass = ConvertMap.normalizeType(object.getClass());
        Class<?> targetClass = ConvertMap.normalizeType(clazz);
        if (sourceClass.equals(targetClass)) {
            return castValue(object, clazz, targetClass);
        }
        if (targetClass.isEnum() && object instanceof String) {
            return convertStringToEnum((String) object, clazz, defaultValue);
        }
        if (sourceClass.isEnum() && String.class.equals(targetClass)) {
            return castValue(((Enum<?>) object).name(), clazz, targetClass);
        }
        if (object instanceof Number && Number.class.isAssignableFrom((Class<?>) targetClass)) {
            return convertNumber((Number) object, clazz, defaultValue);
        }
        try {
            IConverter converter = ConvertMap.getConverter(sourceClass, (Class<?>) targetClass);
            Object convertedValue = converter.convert(object);
            return convertedValue == null ? defaultValue : castValue(convertedValue, clazz, targetClass);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 严格转换，不支持时抛异常
     */
    public static <T> T convertRequired(Object object, Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }
        if (object == null) {
            return null;
        }
        Class<?> sourceClass = ConvertMap.normalizeType(object.getClass());
        Class<?> targetClass = ConvertMap.normalizeType(clazz);
        if (sourceClass.equals(targetClass)) {
            return castValue(object, clazz, targetClass);
        }
        if (targetClass.isEnum() && object instanceof String) {
            T enumValue = convertStringToEnum((String) object, clazz, null);
            if (enumValue == null) {
                throw new UnsupportedClassTypeError("Cannot convert String value '" + object + "' to enum " + targetClass.getName());
            }
            return enumValue;
        }
        if (sourceClass.isEnum() && String.class.equals(targetClass)) {
            return castValue(((Enum<?>) object).name(), clazz, targetClass);
        }
        if (object instanceof Number && Number.class.isAssignableFrom((Class<?>) targetClass)) {
            T numberValue = convertNumber((Number) object, clazz, null);
            if (numberValue != null) {
                return numberValue;
            }
        }
        IConverter converter = ConvertMap.getConverter(sourceClass, (Class<?>) targetClass);
        return castValue(converter.convert(object), clazz, targetClass);
    }

    public static boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
        if (sourceClass == null || targetClass == null) {
            return false;
        }
        Class<?> source = ConvertMap.normalizeType(sourceClass);
        Class<?> target = ConvertMap.normalizeType(targetClass);
        if (source.equals(target)) {
            return true;
        }
        if (target.isEnum() && String.class.equals(source)) {
            return true;
        }
        if (source.isEnum() && String.class.equals(target)) {
            return true;
        }
        if (Number.class.isAssignableFrom(source) && Number.class.isAssignableFrom(target)) {
            return true;
        }
        return ConvertMap.canConvert(source, target);
    }

    private static <T> T convertStringToEnum(String value, Class<T> enumClass, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        String text = value.trim();
        if (text.length() == 0) {
            return defaultValue;
        }
        try {
            Class<?> normalizedEnumClass = ConvertMap.normalizeType(enumClass);
            if (!normalizedEnumClass.isEnum()) {
                return defaultValue;
            }
            Object[] enumConstants = normalizedEnumClass.getEnumConstants();
            if (enumConstants == null) {
                return defaultValue;
            }
            for (Object enumConstant : enumConstants) {
                Enum<?> enumValue = (Enum<?>) enumConstant;
                if (enumValue.name().equals(text)) {
                    return enumClass.cast(enumValue);
                }
            }
            return defaultValue;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    private static <T> T convertNumber(Number number, Class<T> targetClass, T defaultValue) {
        try {
            Class<?> normalizedTargetClass = ConvertMap.normalizeType(targetClass);
            if (normalizedTargetClass.equals(Integer.class)) {
                return castValue(Integer.valueOf(number.intValue()), targetClass, normalizedTargetClass);
            }
            if (normalizedTargetClass.equals(Long.class)) {
                return castValue(Long.valueOf(number.longValue()), targetClass, normalizedTargetClass);
            }
            if (normalizedTargetClass.equals(Double.class)) {
                return castValue(Double.valueOf(number.doubleValue()), targetClass, normalizedTargetClass);
            }
            if (normalizedTargetClass.equals(Float.class)) {
                return castValue(Float.valueOf(number.floatValue()), targetClass, normalizedTargetClass);
            }
            if (normalizedTargetClass.equals(Short.class)) {
                return castValue(Short.valueOf(number.shortValue()), targetClass, normalizedTargetClass);
            }
            if (normalizedTargetClass.equals(Byte.class)) {
                return castValue(Byte.valueOf(number.byteValue()), targetClass, normalizedTargetClass);
            }
            if (normalizedTargetClass.equals(BigDecimal.class)) {
                return castValue(new BigDecimal(number.toString()), targetClass, normalizedTargetClass);
            }
        } catch (Exception ignore) {
        }
        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    private static <T> T castValue(Object value, Class<T> clazz, Class<?> normalizedTargetClass) {
        if (value == null) {
            return null;
        }
        if (clazz.isPrimitive()) {
            return (T) normalizedTargetClass.cast(value);
        }
        return clazz.cast(value);
    }
}
