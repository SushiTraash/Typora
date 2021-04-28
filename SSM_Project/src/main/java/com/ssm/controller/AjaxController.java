package com.ssm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AjaxController {
    @RequestMapping("/hello")
    public String test1(){
        return "hello";
    }

}
