package com.example.controller;

import com.example.domain.entity.Demo;
import com.example.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
   @Autowired
    private DemoService demoService;

    @RequestMapping("/save")
    public String save(){
        Demo d = new Demo();
        d.setName("myDemo");
        demoService.save(d);
        return "ok.Demo2Controller.save";
    }
}
