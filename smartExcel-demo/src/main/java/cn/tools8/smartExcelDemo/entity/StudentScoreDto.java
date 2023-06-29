package cn.tools8.smartExcelDemo.entity;

import cn.tools8.smartExcel.annotaion.ExcelExport;
import cn.tools8.smartExcel.annotaion.ExcelStyle;
import cn.tools8.smartExcel.entity.WriteDataBase;
import cn.tools8.smartExcelDemo.handler.IsPassCellStyleHandler;
import cn.tools8.smartExcelDemo.handler.IsPassValueConverter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tuaobin 2023/6/15$ 14:57$
 */
public class StudentScoreDto extends WriteDataBase implements Serializable {
    @ExcelExport(names = {"学费统计报表", "学成科绩${writeDateChildrenIndex}", "科目"})
    private String subject;
    @ExcelExport(names = {"学费统计报表", "学成科绩${writeDateChildrenIndex}", "得分"})
    private BigDecimal score;
    @ExcelExport(names = {"学费统计报表", "学成科绩${writeDateChildrenIndex}", "级别"})
    @ExcelStyle(autoSizeColumn = true)
    private String level;
    @ExcelExport(names = {"学费统计报表", "学成科绩${writeDateChildrenIndex}", "是否通过"}, converter = IsPassValueConverter.class)
    @ExcelStyle(cellStyleHandler = IsPassCellStyleHandler.class,autoSizeColumn = true)
    private Boolean isPass;

    public StudentScoreDto() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getPass() {
        return isPass;
    }

    public void setPass(Boolean pass) {
        isPass = pass;
    }

    public StudentScoreDto(String subject, BigDecimal score, String level, Boolean isPass) {
        this.subject = subject;
        this.score = score;
        this.level = level;
        this.isPass = isPass;
    }
}
