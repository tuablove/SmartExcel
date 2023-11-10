package cn.tools8.smartExcelDemo;

import cn.tools8.smartExcel.ExcelWriter;
import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.CellData;
import cn.tools8.smartExcel.entity.DynamicColumn;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.entity.definition.ExcelStyleDefinition;
import cn.tools8.smartExcel.handler.IWriteDataCellInitializeStyleHandler;
import cn.tools8.smartExcel.handler.IWriteDataCellStyleHandler;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.interfaces.IExcelWriteCellStyleManager;
import cn.tools8.smartExcelDemo.entity.GradeFeeDynamicColumnDto;
import cn.tools8.smartExcelDemo.entity.StudentScoreDto;
import cn.tools8.smartExcelDemo.handler.DataCellInitializeStyleHandler;
import cn.tools8.smartExcelDemo.handler.TitleCellStyleHandler;
import cn.tools8.smartExcelDemo.handler.TitleExpressionHandler;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.ss.usermodel.CellStyle;

import java.math.BigDecimal;
import java.util.*;

/**
 * test demo
 */
public class WriteDynamicColumnTest {
    public static void main(String[] args) throws Exception {
        writeTitle();
    }

    public static void writeTitle() throws Exception {
        ExcelWriter<GradeFeeDynamicColumnDto> reader = new ExcelWriter<GradeFeeDynamicColumnDto>(GradeFeeDynamicColumnDto.class);
        DynamicColumn dc1 = new DynamicColumn();
        dc1.setTitleNames("学费统计报表", "总分", "总分");
        dc1.setKey("totalScore");
        dc1.setValue("A");
        DynamicColumn dc2 = new DynamicColumn();
        dc2.setTitleNames("学费统计报表", "结论项", "结论项");
        dc2.setKey("finalScore");
        dc2.setValue("通过");
        ExcelStyleDefinition style = new ExcelStyleDefinition();
        style.setCellStyleHandler(new IWriteDataCellStyleHandler() {
            @Override
            public CellStyle onCreating(CellData cellData) {
                if ("通过".equals(cellData.getOriginCellValue())) {
                    return cellData.getStyleManager().getCellStyle("green");
                } else {
                    return cellData.getStyleManager().getCellStyle("red");
                }
            }
        });
        dc2.setStyle(style);

        List<GradeFeeDynamicColumnDto> dataList = new ArrayList<>();
        GradeFeeDynamicColumnDto item = new GradeFeeDynamicColumnDto(1001, "学生甲", "好学生", BigDecimal.valueOf(10000), BigDecimal.valueOf(5000), 125678L, "插班生", new Date());
        item.addMulti(dc1, dc2);
        GradeFeeDynamicColumnDto item1 = new GradeFeeDynamicColumnDto(1002, "学生甲1", "好学生1", BigDecimal.valueOf(9000), BigDecimal.valueOf(3000), 125678L, "原班生", new Date());
        item1.cloneDynamicColumnFrom(item);
        item1.set("totalScore", "B");
        item1.set("finalScore", "不通过");

        GradeFeeDynamicColumnDto item2 = new GradeFeeDynamicColumnDto(1003, "学生甲1", "好学生1", BigDecimal.valueOf(9000), BigDecimal.valueOf(3000), 125678L, "原班生", new Date());
        item2.cloneDynamicColumnFrom(item);
        Map<String, Object> map = new HashMap<>();
        map.put("totalScore", "B");
        map.put("finalScore", "不通过");
        item2.set(map);

        dataList.add(item);
        dataList.add(item1);
        dataList.add(item2);


        for (int i = 0; i < 30000; i++) {
            GradeFeeDynamicColumnDto itemRandom = (GradeFeeDynamicColumnDto) BeanUtils.cloneBean(item);
            item.cloneDynamicColumnTo(itemRandom);
            itemRandom.setName(itemRandom.getName() + i);
            itemRandom.setDynamicColumnValue("totalScore", i % 2 == 0 ? "B" : "A");
            itemRandom.setDynamicColumnValue("finalScore", i % 2 == 0 ? "不通过" : "通过");
            dataList.add(itemRandom);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ExcelWriteConfig config = new ExcelWriteConfig();
        config.setFilePath(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/班费收支明细表-DynamicColumn.xlsx");
//        config.setDefaultSheetName("班费收支明细表");
        //设置名称
        config.setTitleExpressionHandler(new TitleExpressionHandler());
        config.setTitleCellStyleHandler(new TitleCellStyleHandler());
        config.setDataCellInitializeStyleHandler(new DataCellInitializeStyleHandler());
//        config.setExcludeFields("totalScore","finalScore");
//        config.setIncludeFields("totalScore", "finalScore");
        reader.write(dataList, config);
        stopWatch.stop();
        System.out.println("completed : " + stopWatch.formatTime());
    }
}
