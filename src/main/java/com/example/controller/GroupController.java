package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Group;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.GroupService;
import com.example.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/group")
@Controller
public class GroupController extends BaseController{

    @Autowired
    private GroupService groupService;

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

        Page<Group> datas = groupService.findAllNoCriteria(page);
        model.addAttribute("datas",datas);

        return "admin/group_list";
    }

    @RequestMapping("/{id}")
    @LoggerManage(description = "分组详细")
    public String detail(@PathVariable Integer id, Model model){
        Group group = groupService.findOneSuper(id);
        model.addAttribute("group",group);
        return "admin/group_datail";
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
