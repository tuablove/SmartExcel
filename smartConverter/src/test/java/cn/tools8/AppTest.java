package cn.tools8;

import static org.junit.Assert.assertTrue;

import cn.tools8.convert.BaseTypeConverter;
import org.junit.Test;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
//        String convert5 = BaseTypeConverter.convert('1', String.class);
//        String convert = BaseTypeConverter.convert(1, String.class);
//        Byte[] convert2 = BaseTypeConverter.convert("1", Byte[].class);
//        Byte[] convert3 = BaseTypeConverter.convert(new BigDecimal[]{BigDecimal.ONE,BigDecimal.ZERO}, Byte[].class);
        Byte[] convert4 = BaseTypeConverter.convert(new ArrayList<String>(), Byte[].class);
//        System.out.println(convert);
    }
}
