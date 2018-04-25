package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.bean.CommSearch;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.*;
import com.example.domain.enums.SearchType;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.*;
import com.example.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/join")
@Controller
public class JoinController extends BaseController{

    @Autowired
    private JoinService joinService;

    @Autowired
    private ActivityService activityService;

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

    @RequestMapping(value = "/joinNoView/{activityId}")
    @LoggerManage(description = "待报名界面")
    public String joinNoView(@PathVariable Integer activityId, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Activity activity = activityService.findOne(activityId);

        Page<User> datas = userService.findAllNoJoinCriteria(activityId, new UserSearchForm(), page);

        model.addAttribute("activity", activity);
        model.addAttribute("datas", datas);

        model.addAttribute("searchType", SearchType.all);

        assignModel(model);

        return "admin/join_no";
    }

    @RequestMapping("/joinNoView/superSearch/{activityId}")
    @LoggerManage(description = "待报名界面高级搜索列表")
    public String joinNoViewSuperSearch(@PathVariable Integer activityId, Model model, @ModelAttribute(value = "userSearchForm") UserSearchForm userSearchForm, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Activity activity = activityService.findOne(activityId);
        Page<User> datas = userService.findAllNoJoinCriteria(activityId, userSearchForm, page);
        model.addAttribute("datas",datas);

        model.addAttribute("activity", activity);

        model.addAttribute("searchType", SearchType.searchSuper);

        assignModel(model);

//        搜索表单的值，再传入页面
        model.addAttribute("userSearchForm",userSearchForm);

        return "admin/join_no";
    }

    @RequestMapping("/joinNoView/{activityId}/{type}/{value}")
    @LoggerManage(description = "待报名界面BySearch")
    public String joinNoViewByType(Model model, @PathVariable Integer activityId, @PathVariable Integer type, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Activity activity = activityService.findOne(activityId);
        model.addAttribute("activity", activity);
        assignModel(model);
        model.addAttribute("userCommSearch", new CommSearch(type, value));
        model.addAttribute("searchType", SearchType.search);

        if (type == 1 && value != null){
//        根据工号
            Page<User> datas = userService.findAllNoJoinByJobNum(activityId, value, page);
            model.addAttribute("datas",datas);
            return "admin/join_no";
        }else if (type == 2 && value != null){
//        根据姓名
            Page<User> datas = userService.findAllNoJoinByName(activityId, value, page);
            model.addAttribute("datas",datas);
            return "admin/join_no";
        }
//        重定向
        return "redirect:/join/joinNoView/"+activityId;
    }

    @RequestMapping(value = "/joinOkView/{activityId}")
    @LoggerManage(description = "已报名界面")
    public String joinOkView(@PathVariable Integer activityId, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Activity activity = activityService.findOne(activityId);
        Page<Join> datas = joinService.findAllByActivity_ActivityId(activityId, page);
        model.addAttribute("searchType", SearchType.all);
        model.addAttribute("activity", activity);
        model.addAttribute("datas", datas);

        Map<String, String[]> defMap = getDefMap(activity);

        model.addAttribute("defMap", defMap);

        assignModel(model);

        return "admin/join_ok";
    }

    @RequestMapping(value = "/joinOkView/superSearch/{activityId}")
    @LoggerManage(description = "已报名高级搜索界面")
    public String joinOkViewSuperSearch(@PathVariable Integer activityId, @ModelAttribute(value = "userSearchForm") UserSearchForm userSearchForm, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Activity activity = activityService.findOne(activityId);
        Page<Join> datas = joinService.findAllByActivity_ActivityId(activityId, page);
        model.addAttribute("searchType", SearchType.searchSuper);
        model.addAttribute("activity", activity);
        model.addAttribute("datas", datas);

        Map<String, String[]> defMap = getDefMap(activity);
        model.addAttribute("defMap", defMap);

        assignModel(model);

        return "admin/join_ok";
    }

    @RequestMapping(value = "/joinOkView/{activityId}/{type}/{value}")
    @LoggerManage(description = "已报名界面BySearch")
    public String joinOkViewByType(@PathVariable Integer activityId, @PathVariable Integer type, @PathVariable String value, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Activity activity = activityService.findOne(activityId);
        model.addAttribute("activity", activity);
        assignModel(model);
        model.addAttribute("userCommSearch", new CommSearch(type, value));
        model.addAttribute("searchType", SearchType.search);
        Map<String, String[]> defMap = getDefMap(activity);
        model.addAttribute("defMap", defMap);
        if (type == 1 && value != null){
//        根据工号
            Page<Join> datas = joinService.findAllByActivity_ActivityIdAndUser_JobNum(activityId, value, page);
            model.addAttribute("datas",datas);
            return "admin/join_ok";
        }else if (type == 2 && value != null){
//        根据姓名
            Page<Join> datas = joinService.findAllByActivity_ActivityIdAndUser_Name(activityId, value, page);
            model.addAttribute("datas",datas);
            return "admin/join_ok";
        }
//        重定向
        return "redirect:/join/joinOkView/"+activityId;
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

            if (!activityService.canJoin(activityId)){
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
            if (!activityService.canJoin(Integer.parseInt(activityId))){
                return result(ExceptionMsg.JoinDelForCloseFailed);
            }
            joinService.delete(ids);
            return result(ExceptionMsg.JoinDelSuccess);
        }catch (Exception e){
            return result(ExceptionMsg.JoinDelFailed);
        }
    }

    private Model assignModel(Model model){
        //        所有的组
        List<Group> groups = groupService.findAll();

//        所有的民族
        List<Nation> nations = nationService.findAll();

        //        所有的政治面貌
        List<Politics> politicss = politicsService.findAll();

        //        所有的部门
        List<Department> departments = departmentService.findAll();

        //        所有的职务
        List<Duty> duties = dutyService.findAll();

//        用于用户高级搜索
        UserSearchForm userSearchForm = new UserSearchForm();

        model.addAttribute("groups",groups);

        model.addAttribute("nations",nations);

        model.addAttribute("politicss",politicss);

        model.addAttribute("departments",departments);

        model.addAttribute("duties",duties);

        model.addAttribute("userSearchForm",userSearchForm);

        model.addAttribute("userCommSearch", new CommSearch(1, ""));

        return model;
    }

    /**
     * 把activity自定义的列和值放入Map中
     * @param activity
     * @return
     */
    private Map<String, String[]> getDefMap(Activity activity){
        String[] labs = activity.getLabelDefs();
        if (labs == null || labs.length == 0){
            return null;
        }
        String[][] inpss = activity.getInputDefss();
        Map<String, String[]> defMap = new HashMap<>();
        for (int i = 0; i < labs.length; i++){
            defMap.put(labs[i], inpss[i]);
        }
        return defMap;
    }

}
