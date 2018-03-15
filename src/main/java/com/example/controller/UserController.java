package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;
   @RequestMapping("/userList")
   @LoggerManage(description = "组员列表")
    public String userList(Model model){
       List<User> users = userService.findAll();
       model.addAttribute("users",users);
       return "admin/user_list";
    }
}
