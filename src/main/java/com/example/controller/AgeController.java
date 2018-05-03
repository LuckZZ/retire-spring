package com.example.controller;

import com.example.comm.config.Access;
import com.example.domain.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/age")
@Controller
@Access(roles = Role.admin)
public class AgeController {
    @RequestMapping("/ageList")
    public String ageList(){
        return "admin/age_list";
    }
}
