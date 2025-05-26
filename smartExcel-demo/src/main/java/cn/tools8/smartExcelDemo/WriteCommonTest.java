package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelWriter;
import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.SortOrderColumn;
import cn.tools8.smartExcel.handler.IWriteSortOrderHandler;
import cn.tools8.smartExcelDemo.entity.GradeFeeDto;
import cn.tools8.smartExcelDemo.handler.TitleCellStyleHandler;
import cn.tools8.smartExcelDemo.handler.TitleExpressionHandler;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.math.BigDecimal;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class WriteCommonTest {
    public static void main(String[] args) throws Exception {
        writeTitle();
    }
    public static void writeTitle() throws Exception {
        ExcelWriter<GradeFeeDto> reader = new ExcelWriter<GradeFeeDto>(GradeFeeDto.class);
        List<GradeFeeDto> dataList = new ArrayList<>();
        GradeFeeDto item = new GradeFeeDto(1001, "学生甲", "好学生", BigDecimal.valueOf(10000), BigDecimal.valueOf(5000), 125678L, "插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生插班生", new Date());
        GradeFeeDto item1 = new GradeFeeDto(1002, "学生甲1", "好学生1", BigDecimal.valueOf(9000), BigDecimal.valueOf(3000), 125678L, "原班生", new Date());
        dataList.add(item);
        dataList.add(item1);
        for (int i = 0; i < 3000; i++) {
            GradeFeeDto itemRandom = (GradeFeeDto) BeanUtils.cloneBean(item);
            itemRandom.setName(itemRandom.getName() + i);
            dataList.add(itemRandom);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ExcelWriteConfig config = new ExcelWriteConfig();
        config.setFilePath(Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "/班费收支明细表-common.xlsx");
        config.setDefaultSheetName("班费收支明细表");
        //设置名称
        config.setTitleExpressionHandler(new TitleExpressionHandler());
        config.setTitleCellStyleHandler(new TitleCellStyleHandler());
        //全局设置不包含的字段
        config.setExcludeFields("totalScore","comment");
        //排序设置
        config.setSortOrderHandler(new IWriteSortOrderHandler() {
            @Override
            public void sort(Class<?> clazz, List<SortOrderColumn> columns) {
                for (int i = 0; i < columns.size(); i++) {
                    columns.get(i).setOrder(columns.size()-1 - i);
                }
            }
        });
        reader.write(dataList, config);
        stopWatch.stop();
        System.out.println("completed : " + stopWatch.formatTime());
    }
}
