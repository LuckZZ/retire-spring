package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Admin;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController extends BaseController{
    @Autowired
    private AdminService adminService;

    @RequestMapping("/adminList")
    @LoggerManage(description = "管理员列表")
    public String adminList(Model model){
        List<Admin> admins = adminService.findAll();
        model.addAttribute("admins",admins);
        return "admin/admin_list";
    }

    @ResponseBody
    @RequestMapping("/exist/{jobNub}")
    @LoggerManage(description = "管理员存在")
    public Response userList(@PathVariable String jobNub){
        boolean exist = adminService.existsByJobNum(jobNub);
        logger.info("用户存在:"+exist);
        if (exist){
//                用户存在
            return result(ExceptionMsg.JobNumUsed);
        }
//        用户不存在
        return result(ExceptionMsg.JobNumNotUsed);
    }
}
