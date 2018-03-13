package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Group;
import com.example.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/group")
@Controller
public class GroupController {
    @Autowired
    private GroupService groupService;
    @RequestMapping("/groupList")
    @LoggerManage(description = "分组列表")
    public String groupList(){
        return "admin/group_list";
    }
}
