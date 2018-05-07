package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.domain.entity.AgeRange;
import com.example.domain.entity.User;
import com.example.domain.enums.Role;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.AgeRangeService;
import com.example.utils.UserAgePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/age")
@Controller
@Access(roles = Role.admin)
public class AgeController extends BaseController {

    @Autowired
    private AgeRangeService ageRangeService;

    @RequestMapping("/ageList")
    @LoggerManage(description = "年龄统计列表")
    public String ageList(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        UserAgePage datas = ageRangeService.findAllUserAndAge(page);
        model.addAttribute("datas", datas);
        return "admin/age_list";
    }

    @ResponseBody
    @RequestMapping("/save")
    @LoggerManage(description = "年龄统计保存")
    public Response save(HttpServletRequest request, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        String minAge = request.getParameter("minAge");
        String maxAge = request.getParameter("maxAge");
        ageRangeService.save(Integer.parseInt(minAge), Integer.parseInt(maxAge));
        return result(ExceptionMsg.AgeAddSuccess);
    }

    @RequestMapping("/ageSelect")
    @LoggerManage(description = "年龄统计选择")
    public String test(Model model){
        List<AgeRange> ageRanges = ageRangeService.findAll();
        model.addAttribute("ageRanges",ageRanges);
        return "/admin/age_select_load";
    }

    @RequestMapping("/delHtml")
    @LoggerManage(description = "删除年龄统计界面")
    public String delHtml(Model model){
        List<AgeRange> ageRanges = ageRangeService.findAll();
        model.addAttribute("ageRanges",ageRanges);
        return "/admin/age_load";
    }

    @ResponseBody
    @RequestMapping("/delete")
    @LoggerManage(description = "删除年龄统计")
    public Response delete(@RequestParam(value = "ageRangeId") Integer ageRangeId){
        ageRangeService.delete(ageRangeId);
        return result(ExceptionMsg.AgeDelSuccess);
    }
}
