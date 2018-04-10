package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.bean.JoinsDisplay;
import com.example.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/join")
@Controller
public class JoinController extends BaseController{

    @Autowired
    private JoinService joinService;

    @RequestMapping(value = "/joinNoView/{activityId}")
    @LoggerManage(description = "待报名界面")
    public String joinNoView(@PathVariable String activityId, Model model){

        JoinsDisplay joinsDisplay = joinService.joinNo(Integer.parseInt(activityId));

        model.addAttribute("joinsDisplay", joinsDisplay);

        return "admin/join_no";
    }

    @ResponseBody
    @RequestMapping(value = "/joinActivity")
    @LoggerManage(description = "活动报名")
    public String joinActivity(HttpServletRequest request){



 /*       JoinsDisplay joinsDisplay = joinService.joinNo(Integer.parseInt(activityId));

        model.addAttribute("joinsDisplay", joinsDisplay);*/

        return "admin/join_no";
    }
}
