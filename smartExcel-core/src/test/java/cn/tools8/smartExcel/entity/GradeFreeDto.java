package cn.tools8.smartExcel.entity;

import cn.tools8.smartExcel.annotaion.ExcelDateFormat;
import cn.tools8.smartExcel.annotaion.ExcelExport;
import cn.tools8.smartExcel.annotaion.ExcelImport;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tuaobin 2023/6/15$ 14:57$
 */
public class GradeFreeDto extends WriteDataBase implements Serializable {
    @ExcelImport(names = {"学号","学生编号"})
    @ExcelExport(names = {"学费统计报表","个人信息","${sno}"})
//    @ExcelExport(names = {"学费统计报表","学费统计报表","学费统计报表"})
    private Integer number;
    @ExcelExport(names = {"学费统计报表","个人信息","姓名"})
    @ExcelImport(names ="姓名")
    private String name;
    @ExcelExport(names = {"学费统计报表","费用","费用简介"})
    @ExcelImport(names ="费用简介")
    private String description;
    @ExcelExport(names = {"学费统计报表","费用","收入"})
    @ExcelImport(names ="收入")
    private BigDecimal income;

    @ExcelExport(names = {"学费统计报表","费用","支出"})
    @ExcelImport(names ="支出")
    private BigDecimal outcome;
    @ExcelExport(names = {"学费统计报表","凭证号","凭证号"})
    @ExcelImport(names ="凭证号")
    private Long ticketNum;
    @ExcelExport(names = {"学费统计报表","备注","备注"})
    @ExcelImport(names ="备注")
    private String comment;
    @ExcelDateFormat()
    @ExcelExport(names = {"学费统计报表","收集时间","收集时间"})
    @ExcelImport(names ="收集时间")
    private Date createDate;

    public GradeFreeDto() {
    }

    public GradeFreeDto(Integer number, String name, String description, BigDecimal income, BigDecimal outcome, Long ticketNum, String comment, Date createDate) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.income = income;
        this.outcome = outcome;
        this.ticketNum = ticketNum;
        this.comment = comment;
        this.createDate = createDate;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getOutcome() {
        return outcome;
    }

    public void setOutcome(BigDecimal outcome) {
        this.outcome = outcome;
    }

    public Long getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Long ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
