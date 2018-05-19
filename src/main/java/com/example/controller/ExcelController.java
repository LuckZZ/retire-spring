package com.example.controller;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.comm.config.WebSecurityConfig;
import com.example.domain.bean.Login;
import com.example.domain.bean.UserSearchForm;
import com.example.domain.entity.Activity;
import com.example.domain.entity.ActivityDef;
import com.example.domain.entity.Join;
import com.example.domain.entity.User;
import com.example.domain.enums.Role;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.ActivityService;
import com.example.service.JoinService;
import com.example.service.UserService;
import com.example.utils.DataUtils;
import com.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@RequestMapping("/excel")
@Controller
@Access(roles = Role.admin)
public class ExcelController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private JoinService joinService;

    @Autowired
    private ActivityService activityService;

    @ResponseBody
    @RequestMapping("/exportUser")
    @LoggerManage(description = "导出用户表")
    public Response exportUser(ModelMap map, @ModelAttribute(value = "userSearchForm") UserSearchForm userSearchForm, HttpServletRequest request, HttpServletResponse response){

        String exportScope = request.getParameter("exportScope");

        String[] item = request.getParameterValues("item");

        List<User> userList = new ArrayList<>();

        if("all".equals(exportScope)){
            userList = userService.findAllUserCriteria(userSearchForm);
        }else if("selected".equals(exportScope)){
            String[] selectedChecked = request.getParameterValues("selectedChecked");
            userList = userService.findAllByUserIds(DataUtils.turn(selectedChecked));
        }

        List<ExcelExportEntity> beanList = assignBeanList(item);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (User u : userList){
            Map<String, Object> row= addRow(u, item);
            list.add(row);
        }

        export(map,"用户信息","用户信息",beanList,list,request,response);

        return result(ExceptionMsg.SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/exportUserBy/{type}/{value}")
    @LoggerManage(description = "导出用户表")
    public Response exportUserBy(@PathVariable Integer type, @PathVariable String value, ModelMap map, HttpServletRequest request, HttpServletResponse response){

        String exportScope = request.getParameter("exportScope");

        String[] item = request.getParameterValues("item");

        List<User> userList = new ArrayList<>();

        if("all".equals(exportScope)){
            if (type == 1){
                userList = userService.findAllByJobNum(value);
            }else if(type == 2){
                userList = userService.findAllByName(value);
            }
        }else if("selected".equals(exportScope)){
            String[] selectedChecked = request.getParameterValues("selectedChecked");
            userList = userService.findAllByUserIds(DataUtils.turn(selectedChecked));
        }

        List<ExcelExportEntity> beanList = assignBeanList(item);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (User u : userList){
            Map<String, Object> row= addRow(u, item);
            list.add(row);
        }

        export(map,"用户信息","用户信息",beanList,list,request,response);

        return result(ExceptionMsg.SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/exportNoJoinUser/{activityId}")
    @LoggerManage(description = "导出未报名用户表")
    @Access(roles = Role.grouper)
    public Response exportNoJoinUser(HttpSession session, @PathVariable Integer activityId, ModelMap map, @ModelAttribute(value = "userSearchForm") UserSearchForm userSearchForm, HttpServletRequest request, HttpServletResponse response){

        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);

        String exportScope = request.getParameter("exportScope");

        String[] item = request.getParameterValues("item");

        List<User> userList = new ArrayList<>();

        if (login.getRole() == Role.admin){
            if("all".equals(exportScope)){
                userList = userService.findAllNoJoinCriteria(activityId,userSearchForm);
            }else if("selected".equals(exportScope)){
                String[] selectedChecked = request.getParameterValues("selectedChecked");
                userList = userService.findAllByUserIds(DataUtils.turn(selectedChecked));
            }

            List<ExcelExportEntity> beanList = assignBeanList(item);

            beanList.add(new ExcelExportEntity("状态", "status"));

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            for (User u : userList){
                Map<String, Object> row= addRow(u, item);
                row.put("status","未报名");
                list.add(row);
            }

            export(map,"未报名用户","未报名用户信息",beanList,list,request,response);

            return result(ExceptionMsg.SUCCESS);
        }else if(login.getRole() == Role.grouper){
            if("all".equals(exportScope)){
//               给userSearchForm赋值groupId为组长groupId
                userSearchForm.setGroup(String.valueOf(login.getGroup().getGroupId()));
                userList = userService.findAllNoJoinCriteria(activityId,userSearchForm);
            }else if("selected".equals(exportScope)){
                String[] selectedChecked = request.getParameterValues("selectedChecked");
                userList = userService.findAllByUserIds(DataUtils.turn(selectedChecked));
            }

            List<ExcelExportEntity> beanList = assignBeanList(item);

            beanList.add(new ExcelExportEntity("状态", "status"));

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            for (User u : userList){
                Map<String, Object> row= addRow(u, item);
                row.put("status","未报名");
                list.add(row);
            }

            export(map,"未报名用户","未报名用户信息",beanList,list,request,response);

            return result(ExceptionMsg.SUCCESS);
        }
        return result(ExceptionMsg.RoleNoAccess);
    }

    @ResponseBody
    @RequestMapping("/exportNoJoinUserBy/{activityId}/{type}/{value}")
    @LoggerManage(description = "导出未报名用户表")
    @Access(roles = Role.grouper)
    public Response exportNoJoinUserBy(HttpSession session, @PathVariable Integer activityId, @PathVariable Integer type, @PathVariable String value, ModelMap map, HttpServletRequest request, HttpServletResponse response){

        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);

        String exportScope = request.getParameter("exportScope");

        String[] item = request.getParameterValues("item");

        List<User> userList = new ArrayList<>();

        if (login.getRole() == Role.admin){
            if("all".equals(exportScope)){
                if (type == 1){
                    userList = userService.findAllNoJoinByJobNum(activityId, value);
                }else if(type == 2){
                    userList = userService.findAllNoJoinByName(activityId, value);
                }
            }else if("selected".equals(exportScope)){
                String[] selectedChecked = request.getParameterValues("selectedChecked");
                userList = userService.findAllByUserIds(DataUtils.turn(selectedChecked));
            }

            List<ExcelExportEntity> beanList = assignBeanList(item);

            beanList.add(new ExcelExportEntity("状态", "status"));

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            for (User u : userList){
                Map<String, Object> row= addRow(u, item);
                row.put("status","未报名");
                list.add(row);
            }

            export(map,"未报名用户","未报名用户信息",beanList,list,request,response);

            return result(ExceptionMsg.SUCCESS);

        }else if(login.getRole() == Role.grouper){
            if("all".equals(exportScope)){
                if (type == 1){
                    userList = userService.findAllNoJoinByJobNumWithGroupId(login.getGroup().getGroupId() ,activityId, value);
                }else if(type == 2){
                    userList = userService.findAllNoJoinByNameWithGroupId(login.getGroup().getGroupId(), activityId, value);
                }
            }else if("selected".equals(exportScope)){
                String[] selectedChecked = request.getParameterValues("selectedChecked");
                userList = userService.findAllByUserIds(DataUtils.turn(selectedChecked));
            }

            List<ExcelExportEntity> beanList = assignBeanList(item);

            beanList.add(new ExcelExportEntity("状态", "status"));

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            for (User u : userList){
                Map<String, Object> row= addRow(u, item);
                row.put("status","未报名");
                list.add(row);
            }

            export(map,"未报名用户","未报名用户信息",beanList,list,request,response);

            return result(ExceptionMsg.SUCCESS);
        }

        return result(ExceptionMsg.RoleNoAccess);
    }

    @ResponseBody
    @RequestMapping("/exportJoinUser/{activityId}")
    @LoggerManage(description = "导出已报名用户表")
    @Access(roles = Role.grouper)
    public Response exportJoinUser(HttpSession session, @PathVariable Integer activityId, ModelMap map, @ModelAttribute(value = "userSearchForm") UserSearchForm userSearchForm, HttpServletRequest request, HttpServletResponse response){

        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);

        String exportScope = request.getParameter("exportScope");
        String[] item = request.getParameterValues("item");

        String[] inputDefs = request.getParameterValues("inputDefs");
        String attend = request.getParameter("attend");

        List<Join> joinList = new ArrayList<>();

        if (login.getRole() == Role.admin){
            if("all".equals(exportScope)){
                joinList = joinService.findAllCriteria(activityId, inputDefs, attend, userSearchForm);
            }else if("selected".equals(exportScope)){
                String[] selectedChecked = request.getParameterValues("selectedChecked");
                Integer[] joinIds = DataUtils.turn(selectedChecked);
                joinList = joinService.findAllByJoinIds(joinIds);
            }
        }else if(login.getRole() == Role.grouper){
            if("all".equals(exportScope)){
                userSearchForm.setGroup(String.valueOf(login.getGroup().getGroupId()));
                joinList = joinService.findAllCriteria(activityId, inputDefs, attend, userSearchForm);
            }else if("selected".equals(exportScope)){
                String[] selectedChecked = request.getParameterValues("selectedChecked");
                Integer[] joinIds = DataUtils.turn(selectedChecked);
                if (!joinService.existsByJoinIdAndGroupId(joinIds, login.getGroup().getGroupId())){
//                    如果不存在
                    return result(ExceptionMsg.RoleNoAccess);
                }
                joinList = joinService.findAllByJoinIds(joinIds);
            }
        }else {
            return result(ExceptionMsg.RoleNoAccess);
        }

        List<ExcelExportEntity> beanList = assignBeanList(item);

        Activity activity = activityService.findOne(activityId);
        List<ActivityDef> activityDefs = activity.getActivityDefs();

        for (int i =0; i < activityDefs.size(); i++) {
            beanList.add(new ExcelExportEntity(activityDefs.get(i).getLabel(), "label"+i));
        }

        beanList.add(new ExcelExportEntity("是否参加", "attend"));
        beanList.add(new ExcelExportEntity("提交时间", "creatTime"));
        beanList.add(new ExcelExportEntity("状态", "status"));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Join join : joinList){
            Map<String, Object> row= addRow(join.getUser(), item);
            for (int i =0; i < activityDefs.size(); i++) {
                row.put("label"+i,join.getJoinDefs().get(i).getInput());
            }
            row.put("attend",join.getAttend().getName());
            row.put("creatTime", DateUtils.timeStampToFormat(join.getCreateTime()));
            row.put("status","报名成功");
            list.add(row);
        }

        export(map,"已报名用户","已报名用户信息",beanList,list,request,response);

        return result(ExceptionMsg.SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/exportJoinUserBy/{activityId}/{type}/{value}")
    @LoggerManage(description = "导出已报名用户表")
    @Access(roles = Role.grouper)
    public Response exportJoinUserBy(HttpSession session, @PathVariable Integer activityId, @PathVariable Integer type, @PathVariable String value, ModelMap map, HttpServletRequest request, HttpServletResponse response){

        Login login = (Login)session.getAttribute(WebSecurityConfig.SESSION_KEY);

        String exportScope = request.getParameter("exportScope");
        String[] item = request.getParameterValues("item");

        List<Join> joinList = new ArrayList<>();

        if (login.getRole() == Role.admin){
            if("all".equals(exportScope)){
                if (type == 1){
                    joinList = joinService.findAllByActivityIdAndJobNum(activityId, value);
                }else if(type == 2){
                    joinList = joinService.findAllByActivityIdAndName(activityId, value);
                }
            }else if("selected".equals(exportScope)){
                String[] selectedChecked = request.getParameterValues("selectedChecked");
                Integer[] joinIds = DataUtils.turn(selectedChecked);
                joinList = joinService.findAllByJoinIds(joinIds);
            }
        }else if(login.getRole() == Role.grouper){
            if("all".equals(exportScope)){
                if (type == 1){
                    joinList = joinService.findAllByActivityIdAndJobNumWithGroupId(activityId, value, login.getGroup().getGroupId());
                }else if(type == 2){
                    joinList = joinService.findAllByActivityIdAndNameWithGroupId(activityId, value, login.getGroup().getGroupId());
                }
            }else if("selected".equals(exportScope)){
                String[] selectedChecked = request.getParameterValues("selectedChecked");
                Integer[] joinIds = DataUtils.turn(selectedChecked);
                joinList = joinService.findAllByJoinIds(joinIds);
            }
        }else {
            return result(ExceptionMsg.RoleNoAccess);
        }

        List<ExcelExportEntity> beanList = assignBeanList(item);

        Activity activity = activityService.findOne(activityId);
        List<ActivityDef> activityDefs = activity.getActivityDefs();

        for (int i =0; i < activityDefs.size(); i++) {
            beanList.add(new ExcelExportEntity(activityDefs.get(i).getLabel(), "label"+i));
        }

        beanList.add(new ExcelExportEntity("是否参加", "attend"));
        beanList.add(new ExcelExportEntity("提交时间", "creatTime"));
        beanList.add(new ExcelExportEntity("状态", "status"));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Join join : joinList){
            Map<String, Object> row= addRow(join.getUser(), item);
            for (int i =0; i < activityDefs.size(); i++) {
                row.put("label"+i,join.getJoinDefs().get(i).getInput());
            }
            row.put("attend",join.getAttend().getName());
            row.put("creatTime", DateUtils.timeStampToFormat(join.getCreateTime()));
            row.put("status","报名成功");
            list.add(row);
        }

        export(map,"已报名用户","已报名用户信息",beanList,list,request,response);

        return result(ExceptionMsg.SUCCESS);
    }

//    值
    private Map<String, Object> addRow(User u, String[] item){
        Map<String, Object> mapData= new HashMap<>();
        for (String i : item){
            switch (i){
                case "jobNum":
                    mapData.put(i,u.getJobNum());
                    break;
                case "name":
                    mapData.put(i,u.getName());
                    break;
                case "group":
                    mapData.put(i,u.getGroup().getGroupName());
                    break;
                case "rank":
                    mapData.put(i,u.getRank().getName());
                    break;
                case "gender":
                    mapData.put(i,u.getGender().getName());
                    break;
                case "nation":
                    mapData.put(i,u.getNation().getNationName());
                    break;
                case "tel":
                    mapData.put("tel1", u.getTel1());
                    mapData.put("tel2", u.getTel2());
                    mapData.put("tel3", u.getTel3());
                    break;
                case "mate":
                    mapData.put(i,u.getMate());
                    break;
                case "address":
                    mapData.put(i,u.getAddress());
                    break;
                case "department":
                    mapData.put(i,u.getDepartment().getDepartmentName());
                    break;
                case "duty":
                    mapData.put(i,u.getDuty().getDutyName());
                    break;
                case "politics":
                    mapData.put(i,u.getPolitics().getPoliticsName());
                    break;
                case "birth":
                    mapData.put(i,u.getBirth());
                    break;
                case "workTime":
                    mapData.put(i,u.getWorkTime());
                    break;
                case "retireTime":
                    mapData.put(i,u.getRetireTime());
                    break;
                case "passTime":
                    mapData.put(i,u.getPassTime());
                    break;
                case "other":
                    mapData.put(i,u.getOther());
                    break;
            }
        }
        return mapData;
    }

//    表头
    private List<ExcelExportEntity> assignBeanList(String[] item){
        List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
        for (String i : item){
            switch (i){
                case "jobNum":
                    beanList.add(new ExcelExportEntity("工号", "jobNum"));
                    break;
                case "name":
                    beanList.add(new ExcelExportEntity("姓名", "name"));
                    break;
                case "group":
                    beanList.add(new ExcelExportEntity("组别", "group"));
                    break;
                case "rank":
                    beanList.add(new ExcelExportEntity("类型", "rank"));
                    break;
                case "gender":
                    beanList.add(new ExcelExportEntity("性别", "gender"));
                    break;
                case "nation":
                    beanList.add(new ExcelExportEntity("民族", "nation"));
                    break;
                case "tel":
                    beanList.add(new ExcelExportEntity("电话1", "tel1"));
                    beanList.add(new ExcelExportEntity("电话2", "tel2"));
                    beanList.add(new ExcelExportEntity("电话3", "tel3"));
                    break;
                case "mate":
                    beanList.add(new ExcelExportEntity("配偶", "mate"));
                    break;
                case "address":
                    beanList.add(new ExcelExportEntity("地址", "address"));
                    break;
                case "department":
                    beanList.add(new ExcelExportEntity("部门", "department"));
                    break;
                case "duty":
                    beanList.add(new ExcelExportEntity("职务", "duty"));
                    break;
                case "politics":
                    beanList.add(new ExcelExportEntity("政治面貌", "politics"));
                    break;
                case "birth":
                    beanList.add(new ExcelExportEntity("出生日期", "birth"));
                    break;
                case "workTime":
                    beanList.add(new ExcelExportEntity("工作日期", "workTime"));
                    break;
                case "retireTime":
                    beanList.add(new ExcelExportEntity("退休日期", "retireTime"));
                    break;
                case "passTime":
                    beanList.add(new ExcelExportEntity("离世时间", "passTime"));
                    break;
                case "other":
                    beanList.add(new ExcelExportEntity("备注", "other"));
                    break;
            }
        }
        return beanList;
    }

    private void export(ModelMap map, String fileName, String title, List<ExcelExportEntity> beanList, List<Map<String, Object>> list, HttpServletRequest request, HttpServletResponse response){
        ExportParams params = new ExportParams(title,title, ExcelType.XSSF);
        map.put(MapExcelConstants.MAP_LIST, list);
        map.put(MapExcelConstants.ENTITY_LIST, beanList);
        map.put(MapExcelConstants.PARAMS, params);
        map.put(MapExcelConstants.FILE_NAME, fileName);//文件名称
        PoiBaseView.render(map, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
    }

}
