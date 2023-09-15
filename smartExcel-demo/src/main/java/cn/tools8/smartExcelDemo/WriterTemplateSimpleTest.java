package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelWriterTemplateSimple;
import cn.tools8.smartExcel.config.ExcelWriteTemplateConfig;
import cn.tools8.smartExcelDemo.entity.GradeFeeTemplateDto;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class WriterTemplateSimpleTest {
    public static void main(String[] args) throws Exception {
        writeTitle();
    }
    public static void writeTitle() throws Exception {
        ExcelWriterTemplateSimple<GradeFeeTemplateDto> reader = new ExcelWriterTemplateSimple<>(GradeFeeTemplateDto.class);
        List<GradeFeeTemplateDto> dataList = new ArrayList<>();
        GradeFeeTemplateDto item = new GradeFeeTemplateDto(1001, "学生甲", "好学生", BigDecimal.valueOf(10000), BigDecimal.valueOf(5000), 125678L, "插班生", new Date());
        GradeFeeTemplateDto item1 = new GradeFeeTemplateDto(1002, "学生甲1", "好学生1", BigDecimal.valueOf(9000), BigDecimal.valueOf(3000), 125678L, "原班生", new Date());
        dataList.add(item);
        dataList.add(item1);
        for (int i = 0; i < 3; i++) {
            GradeFeeTemplateDto itemRandom = (GradeFeeTemplateDto) BeanUtils.cloneBean(item);
            itemRandom.setName(itemRandom.getName() + i);
            dataList.add(itemRandom);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ExcelWriteTemplateConfig config = new ExcelWriteTemplateConfig();
        config.setTemplateFilePath(Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "/班费收支明细表template.xlsx");
        config.setFilePath(Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "/班费收支明细表templateOut.xlsx");
        config.setDataBeginRowIndex(3);
        reader.write(dataList, config);
        stopWatch.stop();
        System.out.println("completed : " + stopWatch.formatTime());
    }
}
