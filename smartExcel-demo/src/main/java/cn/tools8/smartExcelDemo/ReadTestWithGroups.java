package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelReader;
import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcelDemo.entity.GradeFeeReadDto;

import java.io.FileInputStream;
import java.util.List;

/**
 * 读取excel的数据行到对象
 */
public class ReadTestWithGroups {
    public static void main(String[] args) throws Exception {
        firstTitleRow();
    }
    public static void firstTitleRow() throws Exception {
        ExcelReader<GradeFeeReadDto> reader = new ExcelReader<GradeFeeReadDto>(GradeFeeReadDto.class);
        ExcelReaderConfig config = new ExcelReaderConfig();
        config.setGroups(GradeFeeReadDto.class);
        List<GradeFeeReadDto> read = reader.read(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/班费收支明细表1.xlsx"), config);
        System.out.println(read);
    }
}
