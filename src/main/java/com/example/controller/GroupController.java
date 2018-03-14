package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Group;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/group")
@Controller
public class GroupController extends BaseController{

    @Autowired
    private GroupService groupService;

    @ResponseBody
    @RequestMapping("/groupList")
    @LoggerManage(description = "分组列表")
    public Response groupList(){
        List<Group> groups = groupService.findAll();
        return result(groups, ExceptionMsg.SUCCESS);
    }
}
