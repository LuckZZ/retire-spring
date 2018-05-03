package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.domain.bean.CommSearch;
import com.example.domain.entity.Activity;
import com.example.domain.enums.ActivityStatus;
import com.example.domain.enums.Role;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/activity")
@Access(roles = Role.admin)
public class ActivityController extends BaseController{

    @Autowired
    private ActivityService activityService;


    /**
     *
     * @param activityName
     * @return false：活动存在 true：活动不存在
     */
    @ResponseBody
    @RequestMapping("/exist")
    @LoggerManage(description = "管理员存在")
    public boolean existsAdmin(@RequestParam(value = "activityName") String activityName){
        boolean exist = activityService.existsByActivityName(activityName);
        logger.info("用户存在:"+exist);
        if (!exist){
            return true;
        }
        return false;
    }

    /**
     * 发布活动
     * @param paramId
     * @return
     */
    @ResponseBody
    @RequestMapping("/publish")
    @LoggerManage(description = "发布活动")
    public Response publish(@RequestParam(value = "id") String paramId){
        Integer id = Integer.parseInt(paramId);
        activityService.activityPublish(id);
        return result(ExceptionMsg.ActivityPublishSuccess);
    }

    @RequestMapping("/activityList")
    @LoggerManage(description = "活动列表")
    public String activityList(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("commSearch", new CommSearch(1, ""));
        Page<Activity> datas = activityService.findAllByActivityStatusNot(ActivityStatus.draft, page);
        model.addAttribute("datas",datas);
        return "admin/activity_list";
    }

    @RequestMapping("/draftList")
    @LoggerManage(description = "草稿列表")
    public String draft(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("commSearch", new CommSearch(1, ""));
        Page<Activity> datas = activityService.findAllByActivityStatus(ActivityStatus.draft, page);
        model.addAttribute("datas",datas);
        return "admin/draft_list";
    }

    @RequestMapping("/activityList/{value}")
    @LoggerManage(description = "活动列表ByActivityName")
    public String activityListByActivityName(Model model, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("commSearch", new CommSearch(1, value));
        Page<Activity> datas = activityService.findAllByActivityStatusNotAndActivityName(ActivityStatus.draft, value, page);
        model.addAttribute("datas",datas);
        return "admin/activity_list";
    }

    @RequestMapping("/draftList/{value}")
    @LoggerManage(description = "草稿列表ByGroupName")
    public String draftByActivityName(Model model, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("commSearch", new CommSearch(1, value));
        Page<Activity> datas = activityService.findAllByActivityStatusAndActivityName(ActivityStatus.draft, value, page);
        model.addAttribute("datas",datas);
        return "admin/draft_list";
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

    /**
     * 修改活动界面
     * @param activityId
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateView/{activityId}")
    @LoggerManage(description = "修改活动界面")
    public String updateView(@PathVariable String activityId, Model model){
        Activity activity = activityService.findOne(Integer.parseInt(activityId));
        model.addAttribute("activity",activity);
        return "admin/draft_update";
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @LoggerManage(description = "活动保存")
    public Response save(HttpServletRequest request){
        String activityName = request.getParameter("activityName");
        String[] labelDef = request.getParameterValues("labelDef");
        String[] inputDef = request.getParameterValues("inputDef");

        if(activityService.existsByActivityName(activityName)){
            return  result(ExceptionMsg.ActivityUsed);
        }

        activityService.save(activityName, labelDef, inputDef);

        return result(ExceptionMsg.ActivityAddSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @LoggerManage(description = "活动修改")
    public Response update(HttpServletRequest request){
        String activityId = request.getParameter("activityId");
        String activityName = request.getParameter("activityName");
        String[] labelDef = request.getParameterValues("labelDef");
        String[] inputDef = request.getParameterValues("inputDef");
        activityService.updateById(Integer.parseInt(activityId), activityName, labelDef, inputDef);
        return result(ExceptionMsg.ActivityUpdSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    @LoggerManage(description = "删除活动")
    public Response delete(@RequestParam(value = "id") String paramId){

        Integer activityId = Integer.parseInt(paramId);
        activityService.delete(activityId);

        return result(ExceptionMsg.ActivityDelSuccess);
    }

/*    @ResponseBody
    @RequestMapping("/publish")
    @LoggerManage(description = "发布活动")
    public Response publish(@RequestParam(value = "id") String paramId){
        Integer id = Integer.parseInt(paramId);
        activityService.activityPublish(id);
        return result(ExceptionMsg.ActivityPublishSuccess);
    }*/

    @ResponseBody
    @RequestMapping("/notActivityStatus")
    @LoggerManage(description = "修改活动状态")
    public Response notActivityStatus(@RequestParam(value = "id") String activityId){
        try {
            activityService.notActivityStatus(Integer.parseInt(activityId));
            return result(ExceptionMsg.ActivityStatusSuccess);
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.ActivityStatusFailed);
        }
    }
}
