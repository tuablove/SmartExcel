package cn.tools8.smartExcel;

import static org.junit.Assert.assertTrue;

import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.config.ExcelReaderConfigBuilder;
import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.GradeFreeDto;
import cn.tools8.smartExcel.entity.StudentScoreDto;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
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
        ExcelReader<GradeFreeDto> reader=new ExcelReader<GradeFreeDto>(GradeFreeDto.class);
        List<GradeFreeDto> read = reader.read(new FileInputStream("/Users/tobin/java/git/p/SmartExcel/smartExcel-core/src/test/resources/班费收支明细表1.xlsx"));
        System.out.println(read);
    }

    @Test
    public void writeTitle() throws Exception {
        ExcelWriter<GradeFreeDto> reader=new ExcelWriter<GradeFreeDto>(GradeFreeDto.class);
        List<GradeFreeDto> dataList=new ArrayList<>();
        GradeFreeDto item = new GradeFreeDto(1001,"学生甲","好学生",BigDecimal.valueOf(10000),BigDecimal.valueOf(5000),125678L,"插班生",new Date());
        StudentScoreDto score1Dto=new StudentScoreDto("数学",BigDecimal.valueOf(90),"优秀",true);
        StudentScoreDto score2Dto=new StudentScoreDto("语文",BigDecimal.valueOf(60),"合格",true);
        StudentScoreDto score3Dto=new StudentScoreDto("英语",BigDecimal.valueOf(76),"一般",true);
        List<StudentScoreDto> scoreDtoList=new ArrayList<>();
        scoreDtoList.add(score1Dto);
        scoreDtoList.add(score2Dto);
        scoreDtoList.add(score3Dto);
        item.setWriteDateChildren(scoreDtoList);
        dataList.add(item);
        ExcelWriteConfig config = new ExcelWriteConfig();
        config.setFilePath("/Users/tobin/java/git/p/SmartExcel/smartExcel-core/src/test/resources/班费收支明细表2.xlsx");
        config.setDefaultSheetName("班费收支明细表");
        reader.write(dataList, config);
        System.out.println("completed");
    }
}
