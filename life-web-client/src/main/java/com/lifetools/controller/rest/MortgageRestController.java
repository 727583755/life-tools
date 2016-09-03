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
import com.lifetools.vo.response.MortgageRespVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
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

        List<MortgageRespVo> dataList = new ArrayList<>();
        if (type == 1) {
            Double businessMortgageTotal = paramsVo.getBusinessMortgageTotal()*10000;//元
            Double businessRate = paramsVo.getBusinessRate()/100;
            dataList = processMortgage(businessMortgageTotal, businessRate, month);
        } else if (type == 2) {
            Double cpfMortgageTotal = paramsVo.getCpfMortgageTotal()*10000;//元
            Double cpfRate = paramsVo.getCpfRate()/100;
            dataList = processMortgage(cpfMortgageTotal, cpfRate, month);
        } else if (type ==3) {
            Double businessMortgageTotal = paramsVo.getBusinessMortgageTotal()*10000;//元
            Double cpfMortgageTotal = paramsVo.getCpfMortgageTotal()*10000;//元
            Double businessRate = paramsVo.getBusinessRate()/100;
            Double cpfRate = paramsVo.getCpfRate()/100;

            List<MortgageRespVo> businessList = processMortgage(businessMortgageTotal, businessRate, month);
            List<MortgageRespVo> cpfList = processMortgage(cpfMortgageTotal, cpfRate, month);

            dataList = addList(businessList, cpfList);
        }

        ResponseBase response = new ResponseBase(StatusCode.CODE_200, dataList);
        return GsonUtils.toJson(response);
    }

    private List<MortgageRespVo> addList(List<MortgageRespVo> businessList, List<MortgageRespVo> cpfList) {
        List<MortgageRespVo> rtList = new ArrayList<>();
        for (int i=0; i<businessList.size(); i++) {
            MortgageRespVo vo = new MortgageRespVo();
            MortgageRespVo vo1 = businessList.get(i);
            MortgageRespVo vo2 = cpfList.get(i);

            vo.setMonthCnt(vo1.getMonthCnt());
            vo.setMonthPay(vo1.getMonthPay() + vo2.getMonthPay());
            rtList.add(vo);
        }

        return rtList;
    }

    private List<MortgageRespVo> processMortgage(Double mortgageTotal, Double rate, Integer month) {
        List<MortgageRespVo> list = new ArrayList<MortgageRespVo>();
        for (int i=1; i<=month; i++) {
            Double monthPay = (mortgageTotal*1.0/month) + 1.0*(mortgageTotal - mortgageTotal*1.0/month*(i-1))*rate/12;
            MortgageRespVo respVo = new MortgageRespVo();
            respVo.setMonthCnt(i);
            respVo.setMonthPay(MathUtils.round(String.valueOf(monthPay), 2).doubleValue());
            list.add(respVo);
        }
        return list;
    }
}
