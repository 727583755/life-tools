package com.lifetools.vo.response;

/**
 * Created by Zheng.Ke
 * Date 2016/9/8.
 */
public class MonthDetail {
    /** 期次 */
    private Integer monthCnt;
    /** 每月本息 */
    private Double monthPay;
    /** 每月利息 */
    private Double interestPay;
    /** 每月本金 */
    private Double capitalPay;
    /** 剩余本金 */
    private Double remainCapital;

    public Integer getMonthCnt() {
        return monthCnt;
    }

    public void setMonthCnt(Integer monthCnt) {
        this.monthCnt = monthCnt;
    }

    public Double getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(Double monthPay) {
        this.monthPay = monthPay;
    }

    public Double getInterestPay() {
        return interestPay;
    }

    public void setInterestPay(Double interestPay) {
        this.interestPay = interestPay;
    }

    public Double getCapitalPay() {
        return capitalPay;
    }

    public void setCapitalPay(Double capitalPay) {
        this.capitalPay = capitalPay;
    }

    public Double getRemainCapital() {
        return remainCapital;
    }

    public void setRemainCapital(Double remainCapital) {
        this.remainCapital = remainCapital;
    }
}
