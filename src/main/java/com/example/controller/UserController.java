package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;
   @RequestMapping("/userList")
   @LoggerManage(description = "组员列表")
    public String userList(){
       return "admin/user_list";
    }

    @RequestMapping("/save")
    public String save(){
        User user = userService.findOne(1);
        User clone = (User) user.clone();
        clone.setUserId(null);
        userService.save(clone);
        return "user save is success!";
    }
}
