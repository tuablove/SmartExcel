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
        FileInputStream is = new FileInputStream("/Users/tobin/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/7f34fab5a6bcacb826362dbe615d0873/Message/MessageTemp/97d15043c2a5ce30e4effa725ce99299/File/华中农业大学-水产学院-汇总表-20240408112718004.xlsx");
        List<Map> read = reader.read(is);
        System.out.println(read);
    }
}
