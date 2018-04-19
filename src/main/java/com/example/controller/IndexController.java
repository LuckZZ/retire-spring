package com.example.controller;

import com.example.comm.aop.LoggerManage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class IndexController extends BaseController{

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/admin/index")
    @LoggerManage(description = "管理员首页")
    public String indexAdmin(){
        return "admin/index";
    }

    @RequestMapping("/")
    @LoggerManage(description = "管理员首页")
    public String hello(){
        return "admin/index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
