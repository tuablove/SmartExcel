package cn.tools8.smartExcelDemo.entity;

import cn.tools8.smartExcel.annotaion.ExcelExport;
import cn.tools8.smartExcelDemo.handler.DateWriteConverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tuaobin 2023/6/15$ 14:57$
 */
public class GradeFeeDynamicTemplateDto implements Serializable {

    @ExcelExport(names = {"学号"})
    private Integer number;
    @ExcelExport(names = {"姓名"})
    private String name;
    @ExcelExport(names = {"费用简介"})
    private String description;
    @ExcelExport(names = {"收入"},order = 4)
    private BigDecimal income;

    @ExcelExport(names = {"支出"},order = 5)
    private BigDecimal outcome;
    @ExcelExport(names = {"凭证号"},columnString = "H")
    private Long ticketNum;
    @ExcelExport(names = {"备注"},columnString = "I")
    private String comment;
    @ExcelExport(names = {"日期"},columnString = "J",converter = DateWriteConverter.class)
//    @ExcelStyle(dataFormat = "yyyyMMdd")
    private Date createDate;
    public GradeFeeDynamicTemplateDto() {
    }

    public GradeFeeDynamicTemplateDto(Integer number, String name, String description, BigDecimal income, BigDecimal outcome, Long ticketNum, String comment, Date createDate) {
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
