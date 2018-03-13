package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Admin;
import com.example.domain.enums.LoginType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @RequestMapping("/adminList")
    @LoggerManage(description = "管理员列表")
    public String adminList(Model model){
        return "admin/admin_list";
    }
    @RequestMapping("/grouperList")
    @LoggerManage(description = "组长列表")
    public String grouperList(){
        return "admin/grouper_list";
    }
}
