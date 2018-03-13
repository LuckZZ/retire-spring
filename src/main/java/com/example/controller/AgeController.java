package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/age")
@Controller
public class AgeController {
    @RequestMapping("/ageList")
    public String ageList(){
        return "admin/age_list";
    }
}
