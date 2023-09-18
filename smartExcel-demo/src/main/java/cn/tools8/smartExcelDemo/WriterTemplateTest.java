package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelWriteTemplate;
import cn.tools8.smartExcel.ExcelWriterTemplateSimple;
import cn.tools8.smartExcel.config.ExcelWriteTemplateConfig;
import cn.tools8.smartExcelDemo.entity.GradeFeeTemplateDto;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class WriterTemplateTest {
    public static void main(String[] args) throws Exception {
        writeTitle();
    }
    public static void writeTitle() throws Exception {
        GradeFeeTemplateDto item = new GradeFeeTemplateDto(1001, "学生甲", "好学生", BigDecimal.valueOf(10000), BigDecimal.valueOf(5000), 125678L, "插班生", new Date());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<String, Object> model =new HashMap<>();
        Field[] declaredFields = GradeFeeTemplateDto.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            model.put(declaredField.getName(),  declaredField.get(item));
        }
        model.put("school","北京大学");
        model.put("class","体育班");
        model.put("sum",98765678);
        model.put("createBy","老张");
        model.put("createDate","2023-10-12");
        ExcelWriteTemplate.getInstance().exportExcel(Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "/班费收支明细表template1.xlsx",
                Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "/班费收支明细表templateOut1.xlsx", model);
        stopWatch.stop();
        System.out.println("completed : " + stopWatch.formatTime());
    }
}
