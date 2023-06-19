package cn.tools8.convert;

import cn.tools8.convert.converter.bigDecimalConverter.*;
import cn.tools8.convert.converter.stringConverter.*;
import cn.tools8.convert.exception.UnsupportedClassTypeError;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author tuaobin 2023/6/16$ 10:06$
 */
public class ConvertMap {

    private static Map<ConvertKey, IConverter> map = new HashMap<>();

    public static IConverter getConverter(Class source, Class target) {
        IConverter converter = map.get(new ConvertKey(source, target));
        if (converter == null) {
            throw new UnsupportedClassTypeError(source.getName()+ " convert to " + target.getName() + " is Unsupported");
        }
        return converter;
    }

    static {
        //String
        map.put(new ConvertKey(String.class, Short.class), new StringToShortConverter());
        map.put(new ConvertKey(String.class, Integer.class), new StringToIntegerConverter());
        map.put(new ConvertKey(String.class, Float.class), new StringToFloatConverter());
        map.put(new ConvertKey(String.class, Long.class), new StringToLongConverter());
        map.put(new ConvertKey(String.class, Double.class), new StringToDoubleConverter());
        map.put(new ConvertKey(String.class, BigDecimal.class), new StringToBigDecimalConverter());
        map.put(new ConvertKey(String.class, Character.class), new StringToCharConverter());
        map.put(new ConvertKey(String.class, Boolean.class), new StringToBooleanConverter());
        map.put(new ConvertKey(String.class, Byte[].class), new StringToByteArrayConverter());
        //BigDecimal
        map.put(new ConvertKey(BigDecimal.class, Short.class), new BigDecimalToShortConverter());
        map.put(new ConvertKey(BigDecimal.class, Integer.class), new BigDecimalToIntegerConverter());
        map.put(new ConvertKey(BigDecimal.class, Float.class), new BigDecimalToFloatConverter());
        map.put(new ConvertKey(BigDecimal.class, Long.class), new BigDecimalToLongConverter());
        map.put(new ConvertKey(BigDecimal.class, Double.class), new BigDecimalToDoubleConverter());
        map.put(new ConvertKey(BigDecimal.class, Character.class), new BigDecimalToCharConverter());
        map.put(new ConvertKey(BigDecimal.class, Byte[].class), new BigDecimalToByteArrayConverter());
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
