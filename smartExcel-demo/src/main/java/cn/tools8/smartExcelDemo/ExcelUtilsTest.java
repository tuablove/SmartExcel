package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.utils.ExcelUtils;

/**
 * 只读取excel的标题行
 */
public class ExcelUtilsTest {
    public static void main(String[] args) throws Exception {
        writeCell();
    }
    public static void writeCell() throws Exception {

        String source = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表template.xlsx";
        String target = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表templateWriteCell.xlsx";
        ExcelUtils.copyTo(source,target);
        ExcelUtils.writeCell(target,0,2,null,"异常");
    }
}
