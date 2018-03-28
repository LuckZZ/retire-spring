package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.*;
import com.example.service.*;
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

    @Autowired
    private GroupService groupService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PoliticsService politicsService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DutyService dutyService;

   @RequestMapping("/userList")
   @LoggerManage(description = "组员列表")
    public String userList(Model model){
       List<User> users = userService.findAll();
       model.addAttribute("users",users);
       return "admin/user_list";
    }

    /**
     * 进入增加用户界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/addView")
    @LoggerManage(description = "增加用户界面")
    public String addView(Model model){

//        所有的组
        List<Group> groups = groupService.findAll();

//        所有的民族
        List<Nation> nations = nationService.findAll();

        //        所有的政治面貌
        List<Politics> politicss = politicsService.findAll();

        //        所有的政治面貌
        List<Department> departments = departmentService.findAll();

        //        所有的政治面貌
        List<Duty> duties = dutyService.findAll();

        model.addAttribute("user",new User());

        model.addAttribute("groups",groups);

        model.addAttribute("nations",nations);

        model.addAttribute("politicss",politicss);

        model.addAttribute("departments",departments);

        model.addAttribute("duties",duties);

        return "admin/user_add";
    }
}
