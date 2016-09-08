package com.lifetools.vo.response;

import java.util.List;

/**
 * Created by Zheng.Ke
 * Date 2016/8/25.
 */
public class MortgageRespVo {
    /** 等额本金方式 */
    private MortgageVo avgCapitalType;
    /** 等额本息方式 */
    private MortgageVo avgInterestType;

    public MortgageVo getAvgCapitalType() {
        return avgCapitalType;
    }

    public void setAvgCapitalType(MortgageVo avgCapitalType) {
        this.avgCapitalType = avgCapitalType;
    }

    public MortgageVo getAvgInterestType() {
        return avgInterestType;
    }

    public void setAvgInterestType(MortgageVo avgInterestType) {
        this.avgInterestType = avgInterestType;
    }
}
