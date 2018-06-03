package com.example.controller;

import com.example.comm.Constant;
import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.comm.config.WebSecurityConfig;
import com.example.domain.bean.*;
import com.example.domain.entity.*;
import com.example.domain.enums.JoinStatus;
import com.example.domain.enums.Role;
import com.example.domain.enums.SearchType;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.*;
import com.example.utils.DataUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@RequestMapping("/join")
@Controller
@Access(roles = Role.admin)
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
    @Access(roles = Role.grouper)
    public String joinNoView(HttpSession session, @PathVariable Integer activityId, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Activity activity = activityService.findOne(activityId);
        if (login.getRole() == Role.admin){
            Page<User> datas = userService.findAllNoJoinCriteria(activityId, new JoinUserSearch(), page);
            assignModel(model, activity);
            model.addAttribute("datas", datas);
            return "admin/join_no";
        }else if(login.getRole() == Role.grouper){
            Page<User> datas = userService.findAllNoJoinCriteria(activityId, new JoinUserSearch(String.valueOf(login.getGroup().getGroupId())), page);
            assignModel(model, activity);
            model.addAttribute("datas", datas);
            return "grouper/join_no";
        }
        return Constant.NO_ACCESS_PAGE;
    }

    @RequestMapping("/joinNoView/superSearch/{activityId}")
    @LoggerManage(description = "待报名界面高级搜索列表")
    @Access(roles = Role.grouper)
    public String joinNoViewSuperSearch(HttpSession session, @PathVariable Integer activityId, Model model, @ModelAttribute(value = "joinUserSearch") JoinUserSearch joinUserSearch, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Activity activity = activityService.findOne(activityId);
        assignModel(model, activity);
        if (login.getRole() == Role.admin){
            Page<User> datas = userService.findAllNoJoinCriteria(activityId, joinUserSearch, page);
            model.addAttribute("datas",datas);
//        搜索表单的值，再传入页面
            model.addAttribute("joinUserSearch",joinUserSearch);
            return "admin/join_no";
        }else if(login.getRole() == Role.grouper){
            joinUserSearch.setGroup(String.valueOf(login.getGroup().getGroupId()));
            Page<User> datas = userService.findAllNoJoinCriteria(activityId, joinUserSearch, page);
            model.addAttribute("datas",datas);
//        搜索表单的值，再传入页面
            model.addAttribute("joinUserSearch",joinUserSearch);
            return "grouper/join_no";
        }
        return Constant.NO_ACCESS_PAGE;
    }

    @RequestMapping("/joinNoView/{activityId}/{type}/{value}")
    @LoggerManage(description = "待报名界面BySearch")
    @Access(roles = Role.grouper)
    public String joinNoViewByType(HttpSession session, Model model, @PathVariable Integer activityId, @PathVariable Integer type, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Activity activity = activityService.findOne(activityId);
        assignModel(model, activity);
        model.addAttribute("userCommSearch", new CommSearch(type, value));
        model.addAttribute("searchType", SearchType.search);
        if (login.getRole() == Role.admin){
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
        }else if(login.getRole() == Role.grouper){

            if (type == 1 && value != null){
//        根据工号
                Page<User> datas = userService.findAllNoJoinByJobNumWithGroupId(login.getGroup().getGroupId(),activityId, value, page);
                model.addAttribute("datas",datas);
                return "grouper/join_no";
            }else if (type == 2 && value != null){
//        根据姓名
                Page<User> datas = userService.findAllNoJoinByNameWithGroupId(login.getGroup().getGroupId(), activityId, value, page);
                model.addAttribute("datas",datas);
                return "grouper/join_no";
            }
        }

//        重定向
        return "redirect:/join/joinNoView/"+activityId;
    }

    @RequestMapping(value = "/joinDraftView/{activityId}")
    @LoggerManage(description = "报名草稿界面")
    @Access(roles = Role.grouper)
    public String joinDraftView(HttpSession session, @PathVariable Integer activityId, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Activity activity = activityService.findOne(activityId);
        assignModel(model, activity);
        if (login.getRole() == Role.admin){
            Page<Join> datas = joinService.findAllCriteria(activityId, getDefaultInputs(activity.getActivityDefs().size()), "-1", JoinStatus.draft, new JoinUserSearch(), page);
            model.addAttribute("datas", datas);
            return "admin/join_draft";
        }else if(login.getRole() == Role.grouper){
            Page<Join> datas = joinService.findAllCriteria(activityId, getDefaultInputs(activity.getActivityDefs().size()), "-1", JoinStatus.draft, new JoinUserSearch(), page);
            model.addAttribute("datas", datas);
            return "grouper/join_draft";
        }
        return Constant.NO_ACCESS_PAGE;
    }

    @RequestMapping(value = "/joinDraftView/superSearch/{activityId}")
    @LoggerManage(description = "已报名高级搜索界面")
    @Access(roles = Role.grouper)
    public String joinDraftView(HttpSession session, HttpServletRequest request, @PathVariable Integer activityId, @ModelAttribute(value = "joinUserSearch") JoinUserSearch joinUserSearch, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        String[] inputDefs = request.getParameterValues("inputDefs");
        String attend = request.getParameter("attend");

        Activity activity = activityService.findOne(activityId);

        if (login.getRole() == Role.admin){

            Page<Join> datas = joinService.findAllCriteria(activityId, inputDefs, attend, JoinStatus.draft, joinUserSearch, page);

            assignModel(model, activity);

            model.addAttribute("datas", datas);

            //        搜索表单的值，再传入页面
            joinUserSearch.assignActivityDefSearches(activity.getActivityDefs());
            List<ActivityDefSearch> activityDefSearches = joinUserSearch.getActivityDefSearches();
            for (int i = 0; i < inputDefs.length; i++){
                activityDefSearches.get(i).setOneInput(inputDefs[i]);
            }
            model.addAttribute("joinUserSearch",joinUserSearch);

            return "admin/join_draft";
        }else if(login.getRole() == Role.grouper){
            joinUserSearch.setGroup(String.valueOf(login.getGroup().getGroupId()));
            Page<Join> datas = joinService.findAllCriteria(activityId, inputDefs, attend, JoinStatus.draft, joinUserSearch, page);

            assignModel(model, activity);

            model.addAttribute("datas", datas);

            //        搜索表单的值，再传入页面
            joinUserSearch.assignActivityDefSearches(activity.getActivityDefs());
            List<ActivityDefSearch> activityDefSearches = joinUserSearch.getActivityDefSearches();
            for (int i = 0; i < inputDefs.length; i++){
                activityDefSearches.get(i).setOneInput(inputDefs[i]);
            }
            model.addAttribute("joinUserSearch",joinUserSearch);

            return "grouper/join_draft";
        }
        return Constant.NO_ACCESS_PAGE;
    }


    @RequestMapping(value = "/joinDraftView/{activityId}/{type}/{value}")
    @LoggerManage(description = "报名草稿界面BySearch")
    @Access(roles = Role.grouper)
    public String joinDraftView(HttpSession session, @PathVariable Integer activityId, @PathVariable Integer type, @PathVariable String value, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Activity activity = activityService.findOne(activityId);
        assignModel(model, activity);
        model.addAttribute("userCommSearch", new CommSearch(type, value));
        model.addAttribute("searchType", SearchType.search);

        if (login.getRole() == Role.admin){
            if (type == 1 && value != null){
//        根据工号
                Page<Join> datas = joinService.findAllByActivityIdAndJobNum(activityId, value, JoinStatus.draft, page);
                model.addAttribute("datas",datas);
                return "admin/join_draft";
            }else if (type == 2 && value != null){
//        根据姓名
                Page<Join> datas = joinService.findAllByActivityIdAndName(activityId, value, JoinStatus.draft, page);
                model.addAttribute("datas",datas);
                return "admin/join_draft";
            }
        }else if(login.getRole() == Role.grouper){
            if (type == 1 && value != null){
//        根据工号
                Page<Join> datas = joinService.findAllByActivityIdAndJobNumWithGroupId(activityId, value, login.getGroup().getGroupId(), page);
                model.addAttribute("datas",datas);
                return "admin/join_draft";
            }else if (type == 2 && value != null){
//        根据姓名
                Page<Join> datas = joinService.findAllByActivityIdAndNameWithGroupId(activityId, value, login.getGroup().getGroupId(), page);
                model.addAttribute("datas",datas);
                return "admin/join_draft";
            }
        }
//        重定向
        return "redirect:/join/joinDraftView/"+activityId;
    }

    @RequestMapping(value = "/joinOkView/{activityId}")
    @LoggerManage(description = "已报名界面")
    @Access(roles = Role.grouper)
    public String joinOkView(HttpSession session, @PathVariable Integer activityId, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Activity activity = activityService.findOne(activityId);
        assignModel(model, activity);
        if (login.getRole() == Role.admin){
            Page<Join> datas = joinService.findAllCriteria(activityId, getDefaultInputs(activity.getActivityDefs().size()), "-1", JoinStatus.ultima, new JoinUserSearch(), page);
            model.addAttribute("datas", datas);
            return "admin/join_ok";
        }else if(login.getRole() == Role.grouper){
            Page<Join> datas = joinService.findAllCriteria(activityId, getDefaultInputs(activity.getActivityDefs().size()), "-1", JoinStatus.ultima, new JoinUserSearch(String.valueOf(login.getGroup().getGroupId())), page);
            model.addAttribute("datas", datas);
            return "grouper/join_ok";
        }
        return Constant.NO_ACCESS_PAGE;
    }

    @RequestMapping(value = "/joinOkView/superSearch/{activityId}")
    @LoggerManage(description = "已报名高级搜索界面")
    @Access(roles = Role.grouper)
    public String joinOkViewSuperSearch(HttpSession session, HttpServletRequest request, @PathVariable Integer activityId, @ModelAttribute(value = "joinUserSearch") JoinUserSearch joinUserSearch, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        String[] inputDefs = request.getParameterValues("inputDefs");
        String attend = request.getParameter("attend");

        Activity activity = activityService.findOne(activityId);

        if (login.getRole() == Role.admin){

            Page<Join> datas = joinService.findAllCriteria(activityId, inputDefs, attend, JoinStatus.ultima, joinUserSearch, page);

            assignModel(model, activity);

            model.addAttribute("datas", datas);

            //        搜索表单的值，再传入页面
            joinUserSearch.assignActivityDefSearches(activity.getActivityDefs());
            List<ActivityDefSearch> activityDefSearches = joinUserSearch.getActivityDefSearches();
            for (int i = 0; i < inputDefs.length; i++){
                activityDefSearches.get(i).setOneInput(inputDefs[i]);
            }
            model.addAttribute("joinUserSearch",joinUserSearch);

            return "admin/join_ok";
        }else if(login.getRole() == Role.grouper){
            joinUserSearch.setGroup(String.valueOf(login.getGroup().getGroupId()));
            Page<Join> datas = joinService.findAllCriteria(activityId, inputDefs, attend, JoinStatus.ultima, joinUserSearch, page);

            assignModel(model, activity);

            model.addAttribute("datas", datas);

            //        搜索表单的值，再传入页面
            joinUserSearch.assignActivityDefSearches(activity.getActivityDefs());
            List<ActivityDefSearch> activityDefSearches = joinUserSearch.getActivityDefSearches();
            for (int i = 0; i < inputDefs.length; i++){
                activityDefSearches.get(i).setOneInput(inputDefs[i]);
            }
            model.addAttribute("joinUserSearch",joinUserSearch);

            return "grouper/join_ok";
        }
        return Constant.NO_ACCESS_PAGE;
    }

    @RequestMapping(value = "/joinOkView/{activityId}/{type}/{value}")
    @LoggerManage(description = "已报名界面BySearch")
    @Access(roles = Role.grouper)
    public String joinOkViewByType(HttpSession session, @PathVariable Integer activityId, @PathVariable Integer type, @PathVariable String value, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Activity activity = activityService.findOne(activityId);
        assignModel(model, activity);
        model.addAttribute("userCommSearch", new CommSearch(type, value));
        model.addAttribute("searchType", SearchType.search);

        if (login.getRole() == Role.admin){
            if (type == 1 && value != null){
//        根据工号
                Page<Join> datas = joinService.findAllByActivityIdAndJobNum(activityId, value, JoinStatus.ultima, page);
                model.addAttribute("datas",datas);
                return "admin/join_ok";
            }else if (type == 2 && value != null){
//        根据姓名
                Page<Join> datas = joinService.findAllByActivityIdAndName(activityId, value, JoinStatus.ultima, page);
                model.addAttribute("datas",datas);
                return "admin/join_ok";
            }
        }else if(login.getRole() == Role.grouper){
            if (type == 1 && value != null){
//        根据工号
                Page<Join> datas = joinService.findAllByActivityIdAndJobNumWithGroupId(activityId, value, login.getGroup().getGroupId(), page);
                model.addAttribute("datas",datas);
                return "admin/join_ok";
            }else if (type == 2 && value != null){
//        根据姓名
                Page<Join> datas = joinService.findAllByActivityIdAndNameWithGroupId(activityId, value, login.getGroup().getGroupId(), page);
                model.addAttribute("datas",datas);
                return "admin/join_ok";
            }
        }
//        重定向
        return "redirect:/join/joinOkView/"+activityId;
    }

    @ResponseBody
    @RequestMapping(value = "/joinDraft")
    @LoggerManage(description = "活动报名到草稿")
    @Access(roles = Role.grouper)
    public Response joinDraft(HttpSession session, HttpServletRequest request){

        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);

        try {
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            Integer activityId = Integer.parseInt(request.getParameter("activityId"));
            String[] inputDefs = request.getParameterValues("inputDefs");
            String attend = request.getParameter("attend");
            String other = request.getParameter("other");

//        如果是组长身份，先判断用户id是否在对应的组中
            if (login.getRole() == Role.grouper){
                if (!userService.existsByUserIdAndGroupId(userId, login.getGroup().getGroupId())){
                    return result(ExceptionMsg.RoleNoAccess);
                }
            }

            if (!activityService.canJoin(activityId)){
                return result(ExceptionMsg.JoinForCloseFailed);
            }

            //            如果已经提交
            if (joinService.existsByActivityIdAndUserId(activityId, userId)){
                return result(ExceptionMsg.JoinAlreadyFailed);
            }

            joinService.saveDraft(userId,activityId,inputDefs,attend, other);

            return result(ExceptionMsg.JoinDraftSuccess);
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.JoinDraftFailed);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/joinDraftMul", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @LoggerManage(description = "批量活动报名到草稿")
    @Access(roles = Role.grouper)
    public Response joinDraftMul(HttpSession session, @RequestBody String joinList) throws IOException {
//      session
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);

//      传来的json
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, JoinBean.class);
        List<JoinBean> list = objectMapper.readValue(joinList, javaType);

        List<Integer> userIdList = new ArrayList<>();

        list.forEach(item-> userIdList.add(item.getUserId()));

        //            list转化数组
        Integer[] users = new Integer[userIdList.size()];
        userIdList.toArray(users);

//        如果是组长身份，先判断用户id是否在对应的组中
        if (login.getRole() == Role.grouper){
            if (!userService.existsByUserIdAndGroupId(users, login.getGroup().getGroupId())){
                return result(ExceptionMsg.RoleNoAccess);
            }
        }

//        活动是否开启
        if (!activityService.canJoin(list.get(0).getActivityId())){
            return result(ExceptionMsg.JoinForCloseFailed);
        }

        //            如果已经提交
        if (joinService.existsByActivityIdAndUserId(list.get(0).getActivityId(), users)){
            return result(ExceptionMsg.JoinAlreadyFailed);
        }

//        保存
        try {
            joinService.saveDraft(list);
            return result(ExceptionMsg.JoinDraftSuccess);
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.JoinDraftFailed);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/joinUltima")
    @LoggerManage(description = "活动报名到提交")
    @Access(roles = Role.grouper)
    public Response joinUltima(HttpSession session, HttpServletRequest request){

        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);

        try {
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            Integer activityId = Integer.parseInt(request.getParameter("activityId"));
            String[] inputDefs = request.getParameterValues("inputDefs");
            String attend = request.getParameter("attend");
            String other = request.getParameter("other");

//        如果是组长身份，先判断用户id是否在对应的组中
            if (login.getRole() == Role.grouper){
                if (!userService.existsByUserIdAndGroupId(userId, login.getGroup().getGroupId())){
                    return result(ExceptionMsg.RoleNoAccess);
                }
            }

            if (!activityService.canJoin(activityId)){
                return result(ExceptionMsg.JoinForCloseFailed);
            }

//            如果已经提交
            if (joinService.existsByActivityIdAndUserId(activityId, userId)){
                return result(ExceptionMsg.JoinAlreadyFailed);
            }

            joinService.saveUltima(userId,activityId,inputDefs,attend, other);

            return result(ExceptionMsg.JoinSuccess);
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.JoinFailed);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/joinUltimaMul", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @LoggerManage(description = "批量活动报名到提交")
    @Access(roles = Role.grouper)
    public Response joinUltimaMul(HttpSession session, @RequestBody String joinList) throws IOException {
//      session
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);

//      传来的json
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, JoinBean.class);
        List<JoinBean> list = objectMapper.readValue(joinList, javaType);

        List<Integer> userIdList = new ArrayList<>();

        list.forEach(item-> userIdList.add(item.getUserId()));

        //            list转化数组
        Integer[] users = new Integer[userIdList.size()];
        userIdList.toArray(users);

//        如果是组长身份，先判断用户id是否在对应的组中
        if (login.getRole() == Role.grouper){
            if (!userService.existsByUserIdAndGroupId(users, login.getGroup().getGroupId())){
                return result(ExceptionMsg.RoleNoAccess);
            }
        }

//        活动是否开启
        if (!activityService.canJoin(list.get(0).getActivityId())){
            return result(ExceptionMsg.JoinForCloseFailed);
        }

        //            如果已经提交
        if (joinService.existsByActivityIdAndUserId(list.get(0).getActivityId(), users)){
            return result(ExceptionMsg.JoinAlreadyFailed);
        }

//        保存
        try {
            joinService.saveUltima(list);
            return result(ExceptionMsg.JoinSuccess);
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.JoinFailed);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{activityId}")
    @LoggerManage(description = "删除报名")
    @Access(roles = Role.grouper)
    public Response delete(HttpSession session, @PathVariable Integer activityId, HttpServletRequest request){
        Login login = (Login)session.getAttribute(WebSecurityConfig.SESSION_KEY);
        String[] joinIds = request.getParameterValues("id");
        Integer[] ids = DataUtils.turn(joinIds);
        try {
//            如果joinIds长度为0，则没有获得需要删除的joinId
            if (joinIds.length == 0){
                return result(ExceptionMsg.FAILED);
            }
//            活动关闭，不能删除
            if (!activityService.canJoin(activityId)){
                return result(ExceptionMsg.JoinDelForCloseFailed);
            }

            //        如果是组长身份，先判断用户id是否在对应的组中
            if (login.getRole() == Role.grouper){
//
                Integer[] jIds = DataUtils.turn(joinIds);
              if (!joinService.existsByJoinIdAndGroupId(jIds, login.getGroup().getGroupId(), JoinStatus.ultima)){
                  return result(ExceptionMsg.RoleNoAccess);
              }
            }

            joinService.delete(ids);
            return result(ExceptionMsg.JoinDelSuccess);
        }catch (Exception e){
            return result(ExceptionMsg.JoinDelFailed);
        }
    }

    private Model assignModel(Model model, Activity activity){
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
        JoinUserSearch joinUserSearch = new JoinUserSearch(activity.getActivityDefs());

//       活动
        model.addAttribute("activity", activity);

        model.addAttribute("groups",groups);

        model.addAttribute("nations",nations);

        model.addAttribute("politicss",politicss);

        model.addAttribute("departments",departments);

        model.addAttribute("duties",duties);

        model.addAttribute("userCommSearch", new CommSearch(1, ""));

        model.addAttribute("joinUserSearch",joinUserSearch);

        model.addAttribute("searchType", SearchType.allOrSuper);

        return model;
    }

    /**
     * 草稿报名Join赋值给User
     * @param activityId
     * @param page
     * @return
     */
/*    private List<User> assignJoinDraft(Integer activityId, Page<User> page){
        List<User> users = new ArrayList<>();
        for (User u : page) {
            List<Join> joins = joinService.findAllByActivityIdAndUserIdAndJoinStatus(activityId, u.getUserId(), JoinStatus.draft);
            if (joins.size() > 0){
                u.setJoin(joins.get(0));
            }else {
                u.setJoin(null);
            }
            users.add(u);
        }
        return users;
    }*/

    /**
     *
     * @param defCount
     * @return
     */
    private String[] getDefaultInputs(int defCount){
        String[] temps = new String[defCount];
        for (int i = 0; i < defCount; i++){
            temps[i] = "-1";
        }
        return temps;
    }

}
