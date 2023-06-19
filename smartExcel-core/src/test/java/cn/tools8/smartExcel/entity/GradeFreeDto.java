package cn.tools8.smartExcel.entity;

import cn.tools8.smartExcel.annotaion.ExcelImport;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tuaobin 2023/6/15$ 14:57$
 */
public class GradeFreeDto implements Serializable {
    @ExcelImport(names = {"学号","学生编号"})
    private Integer number;
    @ExcelImport(names ="姓名")
    private String name;
    @ExcelImport(names ="费用简介")
    private String description;
    @ExcelImport(names ="收入")
    private BigDecimal income;
    @ExcelImport(names ="支出")
    private BigDecimal outcome;
    @ExcelImport(names ="凭证号")
    private Long ticketNum;
    @ExcelImport(names ="备注")
    private String comment;
    @ExcelImport(names ="收集时间")
    private Date createDate;

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
