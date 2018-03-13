package com.example.controller;

import com.example.comm.aop.LoggerManage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "Hello World!";
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/admin/index")
    @LoggerManage(description = "管理员首页")
    public String indexAdmin(){
        return "admin/index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
