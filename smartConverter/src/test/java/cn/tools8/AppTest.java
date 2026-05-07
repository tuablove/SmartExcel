package cn.tools8;

import cn.tools8.convert.BaseTypeConverter;
import cn.tools8.convert.exception.UnsupportedClassTypeError;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * BaseTypeConverter tests.
 */
public class AppTest {

    private enum Status {
        OPEN, CLOSED
    }

    @Test
    public void testConvertWithDefaultValue() {
        Integer value = BaseTypeConverter.convert("abc", Integer.class, 99);
        assertEquals(Integer.valueOf(99), value);
    }

    @Test
    public void testConvertNumberToNumberFastPath() {
        Long value = BaseTypeConverter.convert(12.8D, Long.class);
        assertEquals(Long.valueOf(12L), value);
    }

    @Test
    public void testConvertStringToEnum() {
        Status status = BaseTypeConverter.convert("OPEN", Status.class);
        assertEquals(Status.OPEN, status);
    }

    @Test
    public void testConvertEnumToString() {
        String value = BaseTypeConverter.convert(Status.CLOSED, String.class);
        assertEquals("CLOSED", value);
    }

    @Test
    public void testConvertRequiredUnsupported() {
        try {
            BaseTypeConverter.convertRequired(new Date(), Byte[].class);
            fail("Expected UnsupportedClassTypeError");
        } catch (UnsupportedClassTypeError e) {
            assertTrue(e.getMessage().contains("Unsupported"));
        }
    }

    @Test
    public void testCanConvert() {
        assertTrue(BaseTypeConverter.canConvert(String.class, Integer.class));
        assertTrue(BaseTypeConverter.canConvert(Double.class, BigDecimal.class));
        assertTrue(BaseTypeConverter.canConvert(String.class, Status.class));
        assertFalse(BaseTypeConverter.canConvert(Void.class, Date.class));
    }
}
