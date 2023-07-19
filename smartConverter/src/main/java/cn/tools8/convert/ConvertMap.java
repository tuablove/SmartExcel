package cn.tools8.convert;

import cn.tools8.convert.exception.UnsupportedClassTypeError;

import java.util.*;

/**
 * @author tuaobin 2023/6/16$ 10:06$
 */
public class ConvertMap {

    private static Map<ConvertKey, IConverter> map = new HashMap<>();

    public static IConverter getConverter(Class source, Class target) {
        IConverter converter = map.get(new ConvertKey(source, target));
        if (converter == null) {
            String simpleName = obtainSimpleName(source);
            String sourcePackage = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
            String convertClazzName = String.format("cn.tools8.convert.converter.%sConverter.%sTo%sConverter", sourcePackage, simpleName, obtainSimpleName(target));
            Class<?> convertClazz = null;
            try {
                convertClazz = ConvertMap.class.getClassLoader().loadClass(convertClazzName);
                if (convertClazz != null) {
                    converter = (IConverter) convertClazz.newInstance();
                    map.put(new ConvertKey(source, target), converter);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new UnsupportedClassTypeError(source.getName() + " convert to " + target.getName() + " is Unsupported");
            }
        }
        return converter;
    }

    private static String obtainSimpleName(Class sourcePackage) {
        if (List.class.isAssignableFrom(sourcePackage)) {
            return "Lists";
        } else if (sourcePackage.isArray()) {
            return sourcePackage.getComponentType().getSimpleName() + "Array";
        }
        return sourcePackage.getSimpleName();
    }

    public static class ConvertKey {
        private Class source;
        private Class target;


        public ConvertKey(Class source, Class target) {
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
