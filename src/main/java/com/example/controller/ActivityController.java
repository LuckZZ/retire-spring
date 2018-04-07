package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Activity;
import com.example.domain.enums.ActivityStatus;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.ActivityService;
import com.example.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController{

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/activityList")
    @LoggerManage(description = "活动列表")
    public String activityList(Model model){
        List<Activity> activities = activityService.findAllByActivityStatusNot(ActivityStatus.draft);
        model.addAttribute("activities",activities);
        return "admin/activity_list";
    }

    @RequestMapping("/draftList")
    @LoggerManage(description = "草稿箱列表")
    public String draft(Model model){
        List<Activity> activities = activityService.findAllByActivityStatus(ActivityStatus.draft);
        model.addAttribute("activities",activities);
        return "admin/draft_list";
    }

    /**
     * 进入增加活动草稿界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/addDraftView")
    @LoggerManage(description = "增加活动草稿界面")
    public String addDraftView(Model model){
        return "admin/draft_add";
    }

    /**
     * 进入增加活动界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/addActivityView")
    @LoggerManage(description = "增加活动界面")
    public String addActivityView(Model model){
        return "admin/activity_add";
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @LoggerManage(description = "管理员保存")
    public Response save(HttpServletRequest request){
        String activityName = request.getParameter("activityName");
        String[] labelDef = request.getParameterValues("labelDef");
        String[] inputDef = request.getParameterValues("inputDef");

        Activity activity = new Activity(activityName,labelDef,inputDef);

        activityService.save(activity);

        return result(ExceptionMsg.SUCCESS);
    }
}
