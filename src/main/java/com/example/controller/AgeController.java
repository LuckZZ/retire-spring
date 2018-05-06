package com.example.controller;

import com.example.comm.config.Access;
import com.example.domain.entity.User;
import com.example.domain.enums.Role;
import com.example.service.AgeRangeService;
import com.example.utils.UserAgePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/age")
@Controller
@Access(roles = Role.admin)
public class AgeController {

    @Autowired
    private AgeRangeService ageRangeService;

    @RequestMapping("/ageList")
    public String ageList(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        UserAgePage datas = ageRangeService.findAllUserAndAge(page);
        model.addAttribute("datas", datas);
        return "admin/age_list";
    }
}
