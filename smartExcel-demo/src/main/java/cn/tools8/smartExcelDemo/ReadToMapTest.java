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
        FileInputStream is = new FileInputStream("/Users/tobin/Downloads/北京石油化工学院-研究生院-横向汇总表-20240509110359559.xlsx");
        List<Map> read = reader.read(is);
        for (Map map : read) {
            String value = map.get("顺序结果").toString();
            System.out.println(value);
        }
    }
}
