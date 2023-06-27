package cn.tools8.smartExcel;

import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.DynamicColumn;
import cn.tools8.smartExcel.entity.GradeFreeDto;
import cn.tools8.smartExcel.entity.StudentScoreDto;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcel.handler.TitleExpressionHandler;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void multiTitleRow() throws FileNotFoundException {
//        ExcelReader reader=new ExcelReader<>();
//        reader.read(new FileInputStream("/Users/tobin/java/git/p/SmartExcel/smartExcel-core/src/test/resources/班费收支明细表.xlsx"),null,
//                ExcelReaderConfigBuilder.build(2,3));
    }

    @Test
    public void firstTitleRow() throws Exception {
        ExcelReader<GradeFreeDto> reader = new ExcelReader<GradeFreeDto>(GradeFreeDto.class);
        List<GradeFreeDto> read = reader.read(new FileInputStream("/Users/tobin/java/git/p/SmartExcel/smartExcel-core/src/test/resources/班费收支明细表1.xlsx"));
        System.out.println(read);
    }

    @Test
    public void writeTitle() throws Exception {
        ExcelWriter<GradeFreeDto> reader = new ExcelWriter<GradeFreeDto>(GradeFreeDto.class);
        List<GradeFreeDto> dataList = new ArrayList<>();
        GradeFreeDto item = new GradeFreeDto(1001, "学生甲", "好学生", BigDecimal.valueOf(10000), BigDecimal.valueOf(5000), 125678L, "插班生", new Date());
        DynamicColumn dc1 = new DynamicColumn();
        dc1.setTitleNames("学费统计报表", "总分", "总分");
        dc1.setKey("totalScore");
        dc1.setValue("A");
        DynamicColumn dc2 = new DynamicColumn();
        dc2.setTitleNames("学费统计报表", "结论项", "结论项");
        dc2.setKey("finalScore");
        dc2.setValue("通过");
        item.add(dc1);
        item.add(dc2);
        GradeFreeDto item1 = new GradeFreeDto(1002, "学生甲1", "好学生1", BigDecimal.valueOf(9000), BigDecimal.valueOf(3000), 125678L, "原班生", new Date());
        item.cloneDynamicColumnTo(item1);
        item1.setDynamicColumnValue("totalScore","B");
        item1.setDynamicColumnValue("finalScore","不通过");
        StudentScoreDto score1Dto = new StudentScoreDto("数学", BigDecimal.valueOf(90), "优秀", true);
        StudentScoreDto score2Dto = new StudentScoreDto("语文", BigDecimal.valueOf(60), "合格", true);
        StudentScoreDto score3Dto = new StudentScoreDto("英语", BigDecimal.valueOf(56), "不合格(期末7月12号统考,7月29日出结果发学位证)", false);
        List<StudentScoreDto> scoreDtoList = new ArrayList<>();
        scoreDtoList.add(score1Dto);
        scoreDtoList.add(score2Dto);
        scoreDtoList.add(score3Dto);
        item.setWriteDateChildren(scoreDtoList);
        ArrayList<WriteDataBase> dateChildren = new ArrayList<>();
        item1.setWriteDateChildren(dateChildren);
        dateChildren.add(null);
        dateChildren.add(null);
        dateChildren.add(score3Dto);
        dataList.add(item);
        dataList.add(item1);
        ExcelWriteConfig config = new ExcelWriteConfig();
        config.setFilePath("/Users/tobin/java/git/p/SmartExcel/smartExcel-core/src/test/resources/班费收支明细表2.xlsx");
        config.setDefaultSheetName("班费收支明细表");
        config.setTitleExpressionHandler(new TitleExpressionHandler());
        reader.write(dataList, config);
        System.out.println("completed");
    }
}
