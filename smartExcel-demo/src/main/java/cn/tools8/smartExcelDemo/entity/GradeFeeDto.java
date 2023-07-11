package cn.tools8.smartExcelDemo.entity;

import cn.tools8.smartExcel.annotaion.ExcelExport;
import cn.tools8.smartExcel.annotaion.ExcelImport;
import cn.tools8.smartExcel.annotaion.ExcelImportValidateMessage;
import cn.tools8.smartExcel.annotaion.ExcelStyle;
import cn.tools8.smartExcel.entity.WriteDataBase;

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
public class GradeFeeDto implements Serializable {
    @ExcelImport(names = {"学号","学生编号"})
    @ExcelExport(names = {"学费统计报表","个人信息","${sno}"})
    @Max(value = 9999,message = "{name}最大值不能超过{value} ${名称}")
    private Integer number;
    @ExcelExport(names = {"学费统计报表","个人信息","姓名"})
    @ExcelStyle(autoSizeColumn = true)
    @Size(min = 2,max = 20,message = "${名称}长度范围在2-20之间")
    @ExcelImport(names ="姓名")
    private String name;
    @ExcelExport(names = {"学费统计报表","费用","费用简介"})
    @ExcelImport(names ="费用简介")
    private String description;
    @ExcelExport(names = {"学费统计报表","费用","收入"})
    @ExcelImport(names ="收入")
    @Min(value = 30000,message = "{name}不能低于{value}")
    private BigDecimal income;

    @ExcelExport(names = {"学费统计报表","费用","支出"})
    @ExcelImport(names ="支出")
    @Max(value = 2,message = "支出不能高2块%s{名称}")
    private BigDecimal outcome;
    @ExcelExport(names = {"学费统计报表","凭证号","凭证号"})
    @ExcelImport(names ="凭证号")
    private Long ticketNum;
    @ExcelExport(names = {"学费统计报表","备注","备注"})
    @ExcelImport(names ="备注")
    private String comment;
    @ExcelExport(names = {"学费统计报表","收集时间","收集时间"})
    @ExcelStyle(autoSizeColumn = true)
    @ExcelImport(names ="收集时间")
    private Date createDate;
    @ExcelImportValidateMessage
    private String errorMessage;

    @ExcelImportValidateMessage
    private List<String> errorMessageList;
    public GradeFeeDto() {
    }

    public GradeFeeDto(Integer number, String name, String description, BigDecimal income, BigDecimal outcome, Long ticketNum, String comment, Date createDate) {
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public void setErrorMessageList(List<String> errorMessageList) {
        this.errorMessageList = errorMessageList;
    }
}
