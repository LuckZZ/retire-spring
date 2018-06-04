package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.domain.bean.CommSearch;
import com.example.domain.entity.Group;
import com.example.domain.entity.User;
import com.example.domain.enums.Role;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.GroupService;
import com.example.service.UserService;
import com.example.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@RequestMapping("/group")
@Controller
@Access(roles = Role.admin)
public class GroupController extends BaseController{

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/groups")
    @LoggerManage(description = "组集合")
    public Response groups(){
        List<Group> groups = groupService.findAll();
        return result(groups, ExceptionMsg.SUCCESS);
    }

    @RequestMapping("/groupList")
    @LoggerManage(description = "分组列表")
    public String groupList(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("userCommSearch", new CommSearch(1, ""));
        Page<Group> datas = groupService.findAllNoCriteria(page);
        model.addAttribute("datas",datas);
        return "admin/group_list";
    }

    @RequestMapping("/groupList/{value}")
    @LoggerManage(description = "分组列表ByGroupName")
    public String groupListByGroupName(Model model, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("userCommSearch", new CommSearch(1, value));
        Page<Group> datas = groupService.findAllByGroupNameContaining(value, page);
        model.addAttribute("datas",datas);
        return "admin/group_list";
    }

    @RequestMapping("/{id}")
    @LoggerManage(description = "分组详细")
    public String detail(@PathVariable Integer id, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("userCommSearch", new CommSearch(1, ""));
        Page<User> datas = userService.findAllByGroupId(id, page);
        Group group = groupService.findOne(id);
        model.addAttribute("datas",datas);
        model.addAttribute("group",group);
        return "admin/group_datail";
    }

    @RequestMapping("/{id}/{type}/{value}")
    @LoggerManage(description = "分组详细BySearch")
    public String detailByType(@PathVariable Integer id, @PathVariable Integer type, @PathVariable String value, Model model, @RequestParam(value = "page", defaultValue = "0") Integer page){

        model.addAttribute("userCommSearch", new CommSearch(type, value));

        Group group = groupService.findOne(id);
        model.addAttribute("group",group);

        if (type == 1 && value != null){
//        根据工号
            Page<User> datas = userService.findAllByGroupIdAndJobNum(id, value, page);
            model.addAttribute("datas",datas);
            return "admin/group_datail";
        }else if (type == 2 && value != null){
//        根据姓名
            Page<User> datas = userService.findAllByGroupIdAndNameContaining(id, value, page);
            model.addAttribute("datas",datas);
            return "admin/group_datail";
        }
//        重定向
        return "redirect:/group/"+id;
    }

    @ResponseBody
    @RequestMapping("/exist")
    @LoggerManage(description = "组名存在")
    public boolean existsGroupName(@RequestParam(value = "groupName") String groupName){
        boolean exist = groupService.existsByGroupName(groupName);
        logger.info("组名存在:"+exist);
        if (!exist){
            return true;
        }
        return false;
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @LoggerManage(description = "组保存")
    public Response save(@RequestParam(value = "groupName") String groupName){
        if (groupService.existsByGroupName(groupName)){
            return  result(ExceptionMsg.GroupUsed);
        }
//            保存
        groupService.save(new Group(groupName));
        return result(ExceptionMsg.GroupAddSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @LoggerManage(description = "组名修改")
    public Response update(@RequestParam(value = "groupId") String groupId, @RequestParam(value = "groupName") String groupName){
        if (groupService.existsByGroupName(groupName)){
            return  result(ExceptionMsg.GroupUsed);
        }
//        修改
        groupService.updateGroupName(groupName, Integer.parseInt(groupId));
        return result(ExceptionMsg.GroupUpdSuccess);
    }


    @ResponseBody
    @RequestMapping(value = "/delete")
    @LoggerManage(description = "删除组")
    public Response delete(HttpServletRequest request){

        String[] groupIds = request.getParameterValues("id");
        Integer[] ids = DataUtils.turn(groupIds);

        try {
            groupService.delete(ids);
            return result(ExceptionMsg.GroupDelSuccess);
        }catch (Exception e){
            e.printStackTrace();
            return result(ExceptionMsg.GroupDelFailed);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/removeUser")
    @LoggerManage(description = "移除组成员")
    public Response removeUser(HttpServletRequest request){

        String[] groupIds = request.getParameterValues("id");
        Integer[] ids = DataUtils.turn(groupIds);

        try {
            groupService.removeUser(ids);
            return result(ExceptionMsg.GroupRemUserSuccess);
        }catch (Exception e){
            return result(ExceptionMsg.GroupRemUserFailed);
        }
    }

}
