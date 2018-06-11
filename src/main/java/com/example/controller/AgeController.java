package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.domain.bean.CommSearch;
import com.example.domain.entity.Admin;
import com.example.domain.entity.AgeRange;
import com.example.domain.entity.User;
import com.example.domain.enums.Exist;
import com.example.domain.enums.Role;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.AgeRangeService;
import com.example.utils.UserAgePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@RequestMapping("/age")
@Controller
@Access(roles = Role.admin)
public class AgeController extends BaseController {

    @Autowired
    private AgeRangeService ageRangeService;

    @RequestMapping("/ageList")
    @LoggerManage(description = "年龄统计列表")
    public String ageList(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
     /*   UserAgePage datas = ageRangeService.findAllByAgeRange(Exist.yes, -1, page);
        model.addAttribute("datas", datas);
        model.addAttribute("userCommSearch", new CommSearch(1, ""));
        return "admin/age_list";*/
        return "redirect:/age/ageList/3/-1";
    }

    @RequestMapping("/ageList/{type}/{value}")
    @LoggerManage(description = "年龄统计列表BySearch")
    public String ageListByType(Model model, @PathVariable Integer type, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        List<AgeRange> ageRanges = ageRangeService.findAll();
        model.addAttribute("ageRanges",ageRanges);

        model.addAttribute("userCommSearch", new CommSearch(type, value));
        if (type == 1 && value != null){
//        根据工号
            UserAgePage datas = ageRangeService.findAllUserAndAgeByJobNum(value,page);
            model.addAttribute("datas",datas);
            return "admin/age_list";
        }else if (type == 2 && value != null){
//        根据姓名
            UserAgePage datas = ageRangeService.findAllUserAndAgeByName(value,page);
            model.addAttribute("datas",datas);
            return "admin/age_list";
        }else if(type == 3 && value != null){
//        在世
            UserAgePage datas = ageRangeService.findAllByAgeRange(Exist.yes, Integer.parseInt(value), page);
            model.addAttribute("datas",datas);
            return "admin/age_list";
        }else if(type == 4 && value != null){
//         去世
            UserAgePage datas = ageRangeService.findAllByAgeRange(Exist.no, Integer.parseInt(value), page);
            model.addAttribute("datas",datas);
            return "admin/age_list";
        }
//        重定向
        return "redirect:/age/ageList";
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
