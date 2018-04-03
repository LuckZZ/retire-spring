package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Group;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/group")
@Controller
public class GroupController extends BaseController{

    @Autowired
    private GroupService groupService;

    @ResponseBody
    @RequestMapping("/groups")
    @LoggerManage(description = "组集合")
    public Response groups(){
        List<Group> groups = groupService.findAll();
        return result(groups, ExceptionMsg.SUCCESS);
    }

    @RequestMapping("/groupList")
    @LoggerManage(description = "分组列表")
    public String groupList(Model model){
        List<Group> groups = groupService.findAllCustom();
        model.addAttribute("groups",groups);
        return "admin/group_list";
    }

    @RequestMapping("/{id}")
    @LoggerManage(description = "分组详细")
    public String detail(@PathVariable Integer id, Model model){
        Group group = groupService.findOne(id);
        model.addAttribute("group",group);
        return "admin/group_datail";
    }
}
