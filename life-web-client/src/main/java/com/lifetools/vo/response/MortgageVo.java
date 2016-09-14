package com.lifetools.vo.response;

import java.util.List;

/**
 * Created by Zheng.Ke
 * Date 2016/8/25.
 */
public class MortgageVo {
    /** 还款总额 */
    private Double mortgageTotal;
    /** 贷款总额 */
    private Double refundTotal;
    /** 利息总额 */
    private Double interestTotal;
    /** 贷款月数 */
    private Integer monthTotal;
    /** 月均还款 */
    private Double averageMonthPay;
    private List<MonthDetail> monthDetailList;

    public Double getMortgageTotal() {
        return mortgageTotal;
    }

    public void setMortgageTotal(Double mortgageTotal) {
        this.mortgageTotal = mortgageTotal;
    }

    public Double getRefundTotal() {
        return refundTotal;
    }

    public void setRefundTotal(Double refundTotal) {
        this.refundTotal = refundTotal;
    }

    public Double getInterestTotal() {
        return interestTotal;
    }

    public void setInterestTotal(Double interestTotal) {
        this.interestTotal = interestTotal;
    }

    public Integer getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(Integer monthTotal) {
        this.monthTotal = monthTotal;
    }

    public Double getAverageMonthPay() {
        return averageMonthPay;
    }

    public void setAverageMonthPay(Double averageMonthPay) {
        this.averageMonthPay = averageMonthPay;
    }

    public List<MonthDetail> getMonthDetailList() {
        return monthDetailList;
    }

    public void setMonthDetailList(List<MonthDetail> monthDetailList) {
        this.monthDetailList = monthDetailList;
    }
}
