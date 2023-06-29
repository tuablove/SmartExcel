package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelReader;
import cn.tools8.smartExcelDemo.entity.GradeFreeDto;

import java.io.FileInputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class ReadTest {
    public static void main(String[] args) throws Exception {
        firstTitleRow();
    }
    public static void firstTitleRow() throws Exception {
        ExcelReader<GradeFreeDto> reader = new ExcelReader<GradeFreeDto>(GradeFreeDto.class);
        List<GradeFreeDto> read = reader.read(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/班费收支明细表1.xlsx"));
        System.out.println(read);
    }
}
