package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.utils.ExcelCleanUtils;
import cn.tools8.smartExcel.utils.ExcelUtils;

import java.nio.file.Path;

/**
 * 只读取excel的标题行
 */
public class ExcelCleanUtilsTest {
    public static void main(String[] args) throws Exception {
        writeCell();
    }
    public static void writeCell() throws Exception {

        String source = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表template.xlsx";
        String target = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表templateClean.xlsx";

        ExcelUtils.copyTo(source,target);
        ExcelCleanUtils.clean(target,0,3,4);
//        ExcelCleanUtils.clean(source,target,0,3,4);
    }
}
