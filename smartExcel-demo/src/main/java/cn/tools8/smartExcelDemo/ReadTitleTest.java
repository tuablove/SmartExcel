package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.reader.ExcelReaderTitle;

import java.io.FileInputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class ReadTitleTest {
    public static void main(String[] args) throws Exception {
        firstTitleRow();
    }
    public static void firstTitleRow() throws Exception {
        ExcelReaderTitle reader = new ExcelReaderTitle();
        List<String> titles = reader.read(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表1.xlsx"));
        System.out.println(titles);
    }
}
