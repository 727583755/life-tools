package com.lifetools.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zheng.Ke
 * Date 2016/7/1.
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/good")
    public String good() {
        return "good";
    }
}
