package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelReader;
import cn.tools8.smartExcelDemo.entity.GradeFeeDynamicColumnDto;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * 读取excel的数据行到map
 */
public class ReadToMapTest {
    public static void main(String[] args) throws Exception {
        firstTitleRow();
    }
    public static void firstTitleRow() throws Exception {
        ExcelReader<Map> reader = new ExcelReader<Map>(Map.class);
        List<Map> read = reader.read(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表1.xlsx"));
        System.out.println(read);
    }
}
