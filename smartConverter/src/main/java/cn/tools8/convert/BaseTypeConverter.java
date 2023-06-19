package cn.tools8.convert;

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
        if (object == null) {
            return null;
        }
        Class<?> sourceClass = object.getClass();
        if (sourceClass.isAssignableFrom(clazz) || sourceClass.equals(clazz)) {
            return (T) object;
        }
        IConverter converter = ConvertMap.getConverter(sourceClass, clazz);
        return (T) converter.convert(object);
    }
}
