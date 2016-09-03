package com.lifetools.vo.request;

import com.lifetools.commons.validation.group.Business;
import com.lifetools.commons.validation.group.BusinessAndCPF;
import com.lifetools.commons.validation.group.CPF;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Zheng.Ke
 * Date 2016/8/22.
 */
public class MortgageCalculateVo {
    @Min(0)
    @NotNull(groups = {Business.class, BusinessAndCPF.class})
    /** 商业贷款总额 */
    private Double businessMortgageTotal;
    @Min(0)
    @Max(100)
    @NotNull(groups = {Business.class, BusinessAndCPF.class})
    private Double businessRate;


    /** 公积金贷款总额 */
    @Min(0)
    @NotNull(groups = {CPF.class, BusinessAndCPF.class})
    private Double cpfMortgageTotal;
    @Min(0)
    @Max(100)
    @NotNull(groups = {CPF.class, BusinessAndCPF.class})
    private Double cpfRate;

    @Min(1)
    @Max(360)
    @NotNull
    private Integer month;
    @NotNull
    @Min(1)
    @Max(3)
    private Integer type;

    public Double getCpfMortgageTotal() {
        return cpfMortgageTotal;
    }

    public void setCpfMortgageTotal(Double cpfMortgageTotal) {
        this.cpfMortgageTotal = cpfMortgageTotal;
    }

    public Double getBusinessMortgageTotal() {
        return businessMortgageTotal;
    }

    public void setBusinessMortgageTotal(Double businessMortgageTotal) {
        this.businessMortgageTotal = businessMortgageTotal;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getBusinessRate() {
        return businessRate;
    }

    public void setBusinessRate(Double businessRate) {
        this.businessRate = businessRate;
    }

    public Double getCpfRate() {
        return cpfRate;
    }

    public void setCpfRate(Double cpfRate) {
        this.cpfRate = cpfRate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
