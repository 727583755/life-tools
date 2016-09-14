package com.lifetools.controller.rest;

import com.lifetools.commons.constant.StatusCode;
import com.lifetools.commons.util.GsonUtils;
import com.lifetools.commons.util.MathUtils;
import com.lifetools.commons.util.ValidationResult;
import com.lifetools.commons.util.ValidationUtils;
import com.lifetools.commons.validation.group.Business;
import com.lifetools.commons.validation.group.BusinessAndCPF;
import com.lifetools.commons.validation.group.CPF;
import com.lifetools.commons.vo.ResponseBase;
import com.lifetools.vo.request.MortgageCalculateVo;
import com.lifetools.vo.response.MonthDetail;
import com.lifetools.vo.response.MortgageRespVo;
import com.lifetools.vo.response.MortgageVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng.Ke
 * Date 2016/8/22.
 */
@RestController
@RequestMapping("/rest/mortgage")
public class MortgageRestController {


    @RequestMapping(value = "/calculate")
    public String calculate(@RequestBody MortgageCalculateVo paramsVo) {
        Integer type = paramsVo.getType();
        ValidationResult result = ValidationUtils.validateEntity(paramsVo);
        if (result.hasErrors()) {
            return GsonUtils.toJson(new ResponseBase(StatusCode.CODE_401, null, result.getErrorDesc()));
        }
        switch (type) {
            case 1:
                result = ValidationUtils.validateEntity(paramsVo, Business.class);
                break;
            case 2:
                result = ValidationUtils.validateEntity(paramsVo, CPF.class);
                break;
            case 3:
                result = ValidationUtils.validateEntity(paramsVo, BusinessAndCPF.class);
                break;
        }
        if (result.hasErrors()) {
            return GsonUtils.toJson(new ResponseBase(StatusCode.CODE_401, null, result.getErrorDesc()));
        }

        Integer month = paramsVo.getMonth();

        MortgageRespVo mortgageRespVo = new MortgageRespVo();
        MortgageVo avgCapitalType = null;
        MortgageVo avgInterestType = null;
        if (type == 1) {
            Double businessMortgageTotal = paramsVo.getBusinessMortgageTotal()*10000;//元
            Double businessRate = paramsVo.getBusinessRate()/100;
            avgCapitalType = processAvgCapitalMortgage(businessMortgageTotal, businessRate, month);
            avgInterestType = processAvgInterestMortgage(businessMortgageTotal, businessRate, month);
        } else if (type == 2) {
            Double cpfMortgageTotal = paramsVo.getCpfMortgageTotal()*10000;//元
            Double cpfRate = paramsVo.getCpfRate()/100;
            avgCapitalType = processAvgCapitalMortgage(cpfMortgageTotal, cpfRate, month);
            avgInterestType = processAvgInterestMortgage(cpfMortgageTotal, cpfRate, month);
        } else if (type ==3) {
            Double businessMortgageTotal = paramsVo.getBusinessMortgageTotal()*10000;//元
            Double cpfMortgageTotal = paramsVo.getCpfMortgageTotal()*10000;//元
            Double businessRate = paramsVo.getBusinessRate()/100;
            Double cpfRate = paramsVo.getCpfRate()/100;

            avgCapitalType = processAvgCapitalMortgage(businessMortgageTotal, businessRate, month);
            avgInterestType = processAvgInterestMortgage(cpfMortgageTotal, cpfRate, month);
//            dataList = addList(businessList, cpfList);
        }

        mortgageRespVo.setAvgCapitalType(avgCapitalType);
        mortgageRespVo.setAvgInterestType(avgInterestType);
        ResponseBase response = new ResponseBase(StatusCode.CODE_200, mortgageRespVo);
        return GsonUtils.toJson(response);
    }

    /**
     * 等额本息
     * @param mortgageTotal
     * @param rate
     * @param month
     * @return
     */
    private MortgageVo processAvgInterestMortgage(double mortgageTotal, double rate, int month) {
        List<MonthDetail> monthDetailList = new ArrayList<>();
        double monthRate = rate/12;
        double monthPay = mortgageTotal * monthRate * Math.pow((1+monthRate), month) /(Math.pow(1+monthRate, month)-1);
        double refundTotal = month * monthPay;
        double interestTotal = refundTotal - mortgageTotal;

        double refundCapitalTotal = 0;//已还本金
        for (int i=1; i<=month; i++) {
            int monthCnt = i;
            double capitalPay = mortgageTotal * monthRate * Math.pow((1+monthRate), monthCnt-1) / (Math.pow(1+monthRate, month)-1);
            double interestPay = monthPay - capitalPay;
            refundCapitalTotal += capitalPay;
            double remainCapital = mortgageTotal - refundCapitalTotal;

            MonthDetail detail = new MonthDetail();
            detail.setMonthCnt(monthCnt);
            detail.setMonthPay(MathUtils.round(String.valueOf(monthPay), 2).doubleValue());
            detail.setCapitalPay(MathUtils.round(String.valueOf(capitalPay), 2).doubleValue());
            detail.setInterestPay(MathUtils.round(String.valueOf(interestPay), 2).doubleValue());
            detail.setRemainCapital(MathUtils.round(String.valueOf(remainCapital), 2).doubleValue());
            monthDetailList.add(detail);
        }

        double averageMonthPay = refundTotal/month;

        MortgageVo mortgageVo = new MortgageVo();
        mortgageVo.setMonthTotal(month);
        mortgageVo.setMortgageTotal(mortgageTotal);
        mortgageVo.setAverageMonthPay(MathUtils.round(String.valueOf(averageMonthPay), 2).doubleValue());
        mortgageVo.setMonthDetailList(monthDetailList);
        mortgageVo.setInterestTotal(MathUtils.round(String.valueOf(interestTotal), 0).doubleValue());
        mortgageVo.setRefundTotal(MathUtils.round(String.valueOf(refundTotal), 0).doubleValue());
        return mortgageVo;
    }

//    private List<MortgageRespVo> addList(List<MortgageRespVo> businessList, List<MortgageRespVo> cpfList) {
//        List<MortgageRespVo> rtList = new ArrayList<>();
//        for (int i=0; i<businessList.size(); i++) {
//            MortgageRespVo vo = new MortgageRespVo();
//            MortgageRespVo vo1 = businessList.get(i);
//            MortgageRespVo vo2 = cpfList.get(i);
//
//            vo.setMonthCnt(vo1.getMonthCnt());
//            vo.setMonthPay(vo1.getMonthPay() + vo2.getMonthPay());
//            rtList.add(vo);
//        }
//
//        return rtList;
//    }

    /**
     * 等额本金
     * @param mortgageTotal
     * @param rate
     * @param month
     * @return
     */
    private MortgageVo processAvgCapitalMortgage(double mortgageTotal, double rate, int month) {
        List<MonthDetail> monthDetailList = new ArrayList<>();
        double monthRate = rate/12;
        for (int i=1; i<=month; i++) {
            double interestPay = 1.0*(mortgageTotal - mortgageTotal*1.0/month*(i-1))*monthRate;
            int monthCnt = i;
            double capitalPay = mortgageTotal*1.0/month;
            double remainCapital = mortgageTotal - capitalPay*monthCnt;
            double monthPay = capitalPay + interestPay;

            MonthDetail detail = new MonthDetail();
            detail.setMonthCnt(monthCnt);
            detail.setMonthPay(MathUtils.round(String.valueOf(monthPay), 2).doubleValue());
            detail.setCapitalPay(MathUtils.round(String.valueOf(capitalPay), 2).doubleValue());
            detail.setInterestPay(MathUtils.round(String.valueOf(interestPay), 2).doubleValue());
            detail.setRemainCapital(MathUtils.round(String.valueOf(remainCapital), 2).doubleValue());
            monthDetailList.add(detail);
        }
        double interestTotal = (month+1) * mortgageTotal * monthRate/2;
        double refundTotal = mortgageTotal + interestTotal;
        double averageMonthPay = refundTotal/month;

        MortgageVo mortgageVo = new MortgageVo();
        mortgageVo.setMonthTotal(month);
        mortgageVo.setMortgageTotal(mortgageTotal);
        mortgageVo.setAverageMonthPay(MathUtils.round(String.valueOf(averageMonthPay), 2).doubleValue());
        mortgageVo.setMonthDetailList(monthDetailList);
        mortgageVo.setInterestTotal(MathUtils.round(String.valueOf(interestTotal), 0).doubleValue());
        mortgageVo.setRefundTotal(MathUtils.round(String.valueOf(refundTotal), 0).doubleValue());
        return mortgageVo;
    }
}
