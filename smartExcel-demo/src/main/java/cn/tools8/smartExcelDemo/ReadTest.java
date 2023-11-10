package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelReader;
import cn.tools8.smartExcelDemo.entity.GradeFeeDynamicColumnDto;
import cn.tools8.smartExcelDemo.entity.GradeFeeReadDto;

import java.io.FileInputStream;
import java.util.List;

/**
 * 读取excel的数据行到对象
 */
public class ReadTest {
    public static void main(String[] args) throws Exception {
        firstTitleRow();
    }
    public static void firstTitleRow() throws Exception {
        ExcelReader<GradeFeeReadDto> reader = new ExcelReader<GradeFeeReadDto>(GradeFeeReadDto.class);
        List<GradeFeeReadDto> read = reader.read(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/班费收支明细表1.xlsx"));
        System.out.println(read);
    }
}
