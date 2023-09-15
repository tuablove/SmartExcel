package cn.tools8.smartExcelDemo.entity;

import cn.tools8.smartExcel.annotaion.ExcelExport;
import cn.tools8.smartExcel.annotaion.ExcelImport;
import cn.tools8.smartExcel.annotaion.ExcelImportValidateMessage;
import cn.tools8.smartExcel.annotaion.ExcelStyle;
import cn.tools8.smartExcel.handler.IWriteValueConverter;
import cn.tools8.smartExcelDemo.handler.DateWriteConverter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tuaobin 2023/6/15$ 14:57$
 */
public class GradeFeeTemplateDto implements Serializable {

    @ExcelExport(columnString = "A")
    private Integer number;
    @ExcelExport(order = 1)
    private String name;
    @ExcelExport(columnString = "C")
    private String description;
    @ExcelExport(names = {"学费统计报表","费用","收入"},order = 4)
    private BigDecimal income;

    @ExcelExport(names = {"学费统计报表","费用","支出"},order = 5)
    private BigDecimal outcome;
    @ExcelExport(names = {"学费统计报表","凭证号","凭证号"},columnString = "H")
    private Long ticketNum;
    @ExcelExport(names = {"学费统计报表","备注","备注"},columnString = "I")
    private String comment;
    @ExcelExport(names = {"学费统计报表","备注","备注"},columnString = "J",converter = DateWriteConverter.class)
//    @ExcelStyle(dataFormat = "yyyyMMdd")
    private Date createDate;
    public GradeFeeTemplateDto() {
    }

    public GradeFeeTemplateDto(Integer number, String name, String description, BigDecimal income, BigDecimal outcome, Long ticketNum, String comment, Date createDate) {
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
