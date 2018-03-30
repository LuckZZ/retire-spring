package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Grouper;
import com.example.service.GrouperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/grouper")
@Controller
public class GrouperController {
    @Autowired
    GrouperService grouperService;
    @RequestMapping("/grouperList")
    @LoggerManage(description = "组长列表")
    public String grouperList(Model model){
        List<Grouper> groupers = grouperService.findAll();
        model.addAttribute("groupers",groupers);
        return "admin/grouper_list";
    }

}
