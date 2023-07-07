package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelReader;
import cn.tools8.smartExcel.utils.ValidatorUtil;
import cn.tools8.smartExcelDemo.entity.GradeFeeDto;
import cn.tools8.smartExcelDemo.entity.GradeFeeDynamicColumnDto;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class ReadValidateTest {
    public static void main(String[] args) throws Exception {
        test();
    }
    public static void test() throws Exception {
        ExcelReader<GradeFeeDto> reader = new ExcelReader<GradeFeeDto>(GradeFeeDto.class);
        List<GradeFeeDto> read = reader.read(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/班费收支明细表1.xlsx"));
        System.out.println(read);
    }
}
