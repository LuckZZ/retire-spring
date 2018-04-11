package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.bean.JoinsDisplay;
import com.example.domain.entity.Activity;
import com.example.domain.entity.Join;
import com.example.domain.enums.ActivityStatus;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.ActivityService;
import com.example.service.JoinService;
import com.example.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/join")
@Controller
public class JoinController extends BaseController{

    @Autowired
    private JoinService joinService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = "/joinNoView/{activityId}")
    @LoggerManage(description = "待报名界面")
    public String joinNoView(@PathVariable String activityId, Model model){

        JoinsDisplay joinsDisplay = joinService.joinNo(Integer.parseInt(activityId));

        model.addAttribute("joinsDisplay", joinsDisplay);

        return "admin/join_no";
    }

    @RequestMapping(value = "/joinOkView/{activityId}")
    @LoggerManage(description = "已报名界面")
    public String joinOkView(@PathVariable String activityId, Model model){

        Integer id = Integer.parseInt(activityId);

        Activity activity = activityService.findOne(id);

        List<Join> joins = joinService.findAllByActivity_ActivityId(id);

        model.addAttribute("activity", activity);

        model.addAttribute("joins", joins);

        return "admin/join_ok";
    }

    @ResponseBody
    @RequestMapping(value = "/joinActivity")
    @LoggerManage(description = "活动报名")
    public Response joinActivity(HttpServletRequest request){

        try {
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            Integer activityId = Integer.parseInt(request.getParameter("activityId"));
            String[] inputDefs = request.getParameterValues("inputDefs");
            String attend = request.getParameter("attend");

            Activity activity = activityService.findOne(activityId);
            if (activity.getActivityStatus() == ActivityStatus.close){
                return result(ExceptionMsg.JoinForCloseFailed);
            }

            joinService.save(userId,activityId,inputDefs,attend);

            return result(ExceptionMsg.JoinSuccess);
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.JoinFailed);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{activityId}")
    @LoggerManage(description = "删除报名")
    public Response delete(@PathVariable String activityId, HttpServletRequest request){

        String[] joinIds = request.getParameterValues("id");
        Integer[] ids = DataUtils.turn(joinIds);

        try {

            Activity activity = activityService.findOne(Integer.parseInt(activityId));
            if (activity.getActivityStatus() == ActivityStatus.close){
                return result(ExceptionMsg.JoinDelForCloseFailed);
            }

            joinService.delete(ids);
            return result(ExceptionMsg.JoinDelSuccess);
        }catch (Exception e){
            return result(ExceptionMsg.JoinDelFailed);
        }
    }
}
