package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Admin;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/adminList")
    @LoggerManage(description = "管理员列表")
    public String adminList(Model model){
        List<Admin> admins = adminService.findAll();
        model.addAttribute("admins",admins);
        return "admin/admin_list";
    }
}
