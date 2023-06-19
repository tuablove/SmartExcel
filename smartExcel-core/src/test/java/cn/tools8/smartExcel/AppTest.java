package cn.tools8.smartExcel;

import static org.junit.Assert.assertTrue;

import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.config.ExcelReaderConfigBuilder;
import cn.tools8.smartExcel.entity.GradeFreeDto;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    public void multiTitleRow() throws FileNotFoundException {
//        ExcelReader reader=new ExcelReader<>();
//        reader.read(new FileInputStream("/Users/tobin/java/git/p/SmartExcel/smartExcel-core/src/test/resources/班费收支明细表.xlsx"),null,
//                ExcelReaderConfigBuilder.build(2,3));
    }

    @Test
    public void firstTitleRow() throws Exception {
        ExcelReader<GradeFreeDto> reader=new ExcelReader<GradeFreeDto>(GradeFreeDto.class);
        List<GradeFreeDto> read = reader.read(new FileInputStream("/Users/tobin/java/git/p/SmartExcel/smartExcel-core/src/test/resources/班费收支明细表1.xlsx"));
        System.out.println(read);
    }
}
