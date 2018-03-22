package com.example.controller;

import com.example.comm.aop.LoggerManage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/")
@Controller
public class IndexController extends BaseController{

/*    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "Hello World!";
    }*/
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

    @RequestMapping("/demoAjax")
    public String demoAjax(){
        return "demoAjax";
    }


    /**
     * 直接页面跳转，不需要数据交互的，经过此controller
     * @param directory 文件夹
     * @param page 去掉后缀的页面名称
     * @return
     */
/*    @RequestMapping("/direct/{directory}/{page}")
    @LoggerManage(description = "直接跳转")
    public String direct(@PathVariable String directory,@PathVariable String page){
        String url = directory+"/"+page;
        logger.info("跳转到:"+url);
        return url;
    }*/
}
