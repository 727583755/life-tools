package com.lifetools.controller.rest;

import com.lifetools.commons.constant.StatusCode;
import com.lifetools.commons.util.GsonUtils;
import com.lifetools.commons.util.MathUtils;
import com.lifetools.commons.util.ValidationResult;
import com.lifetools.commons.util.ValidationUtils;
import com.lifetools.commons.vo.ResponseBase;
import com.lifetools.vo.request.MortgageCalculateVo;
import com.lifetools.vo.response.MortgageRespVo;
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
    public String calculate(@RequestBody MortgageCalculateVo pramsVo) {
        ValidationResult result = ValidationUtils.validateEntity(pramsVo);
        if (result.hasErrors()) {
            return GsonUtils.toJson(new ResponseBase(StatusCode.CODE_401, null, result.getErrorDesc()));
        }

        Double mortgageTotal = pramsVo.getMortgageTotal()*10000;//元
        Integer month = pramsVo.getMonth();
        Double rate = pramsVo.getRate()/100;

        List<MortgageRespVo> list = new ArrayList<MortgageRespVo>();
        for (int i=1; i<=month; i++) {
            Double monthPay = (mortgageTotal*1.0/month) + 1.0*(mortgageTotal - mortgageTotal*1.0/month*(i-1))*rate/12;
            MortgageRespVo respVo = new MortgageRespVo();
            respVo.setMonthCnt(i);
            respVo.setMonthPay(MathUtils.round(String.valueOf(monthPay), 2).doubleValue());
            list.add(respVo);
        }

        ResponseBase response = new ResponseBase(StatusCode.CODE_200, list);
        return GsonUtils.toJson(response);
    }
}
