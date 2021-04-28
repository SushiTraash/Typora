package com.ajax.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AjaxController {

    @RequestMapping("/hello1")

    public String test1(){
        return "hello";
    }

    @RequestMapping("/a1")
    public void ajax1(String name, HttpServletResponse response) throws IOException {

        if(name.equals("pinkguy")){
            response.getWriter().write("true");
        }else{
            response.getWriter().write("false");
        }

    }
    @RequestMapping("/a2")
    public void ajax2(){

    }

}
