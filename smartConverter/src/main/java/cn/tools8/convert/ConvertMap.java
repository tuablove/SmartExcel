package cn.tools8.convert;

import cn.tools8.convert.exception.UnsupportedClassTypeError;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tuaobin 2023/6/16$ 10:06$
 */
public class ConvertMap {

    private static final Map<ConvertKey, IConverter> map = new ConcurrentHashMap<ConvertKey, IConverter>();
    private static final Map<ConvertKey, Boolean> unsupportedMap = new ConcurrentHashMap<ConvertKey, Boolean>();

    public static IConverter getConverter(Class<?> source, Class<?> target) {
        ConvertKey key = new ConvertKey(source, target);
        if (unsupportedMap.containsKey(key)) {
            throw new UnsupportedClassTypeError(buildUnsupportedMessage(source, target));
        }
        IConverter converter = map.get(key);
        if (converter == null) {
            String simpleName = obtainSimpleName(source);
            String sourcePackage = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
            String convertClazzName = String.format("cn.tools8.convert.converter.%sConverter.%sTo%sConverter", sourcePackage, simpleName, obtainSimpleName(target));
            try {
                Class<?> convertClazz = ConvertMap.class.getClassLoader().loadClass(convertClazzName);
                converter = (IConverter) convertClazz.getDeclaredConstructor().newInstance();
                map.put(key, converter);
            } catch (Exception e) {
                unsupportedMap.put(key, Boolean.TRUE);
                throw new UnsupportedClassTypeError(buildUnsupportedMessage(source, target));
            }
        }
        return converter;
    }

    private static String buildUnsupportedMessage(Class<?> source, Class<?> target) {
        return source.getName() + " convert to " + target.getName() + " is Unsupported";
    }

    public static boolean canConvert(Class<?> source, Class<?> target) {
        if (source == null || target == null) {
            return false;
        }
        if (normalizeType(source).equals(normalizeType(target))) {
            return true;
        }
        try {
            getConverter(normalizeType(source), normalizeType(target));
            return true;
        } catch (UnsupportedClassTypeError e) {
            return false;
        }
    }

    private static String obtainSimpleName(Class<?> sourcePackage) {
        if (List.class.isAssignableFrom(sourcePackage)) {
            return "Lists";
        } else if (sourcePackage.isArray()) {
            return sourcePackage.getComponentType().getSimpleName() + "Array";
        }
        return sourcePackage.getSimpleName();
    }

    public static Class<?> normalizeType(Class<?> sourceType) {
        if (sourceType == null) {
            return null;
        }
        if (!sourceType.isPrimitive()) {
            return sourceType;
        }
        if (sourceType == Integer.TYPE) {
            return Integer.class;
        }
        if (sourceType == Long.TYPE) {
            return Long.class;
        }
        if (sourceType == Double.TYPE) {
            return Double.class;
        }
        if (sourceType == Float.TYPE) {
            return Float.class;
        }
        if (sourceType == Short.TYPE) {
            return Short.class;
        }
        if (sourceType == Byte.TYPE) {
            return Byte.class;
        }
        if (sourceType == Boolean.TYPE) {
            return Boolean.class;
        }
        if (sourceType == Character.TYPE) {
            return Character.class;
        }
        return sourceType;
    }

    public static class ConvertKey {
        private Class<?> source;
        private Class<?> target;


        public ConvertKey(Class<?> source, Class<?> target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConvertKey)) return false;
            ConvertKey that = (ConvertKey) o;
            return Objects.equals(source, that.source) && Objects.equals(target, that.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, target);
        }
    }
}
