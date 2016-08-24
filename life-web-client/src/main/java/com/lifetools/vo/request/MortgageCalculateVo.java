package com.lifetools.vo.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by Zheng.Ke
 * Date 2016/8/22.
 */
public class MortgageCalculateVo {
    @Min(0)
    private Double mortgageTotal;
    @Min(1)
    @Max(360)
    private Integer month;
    @Min(0)
    @Max(100)
    private Double rate;

    public Double getMortgageTotal() {
        return mortgageTotal;
    }

    public void setMortgageTotal(Double mortgageTotal) {
        this.mortgageTotal = mortgageTotal;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
