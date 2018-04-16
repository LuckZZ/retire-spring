package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.domain.entity.Grouper;
import com.example.domain.entity.User;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.GrouperService;
import com.example.service.UserService;
import com.example.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/grouper")
@Controller
public class GrouperController extends BaseController{
    @Autowired
    private GrouperService grouperService;

    @Autowired
    private UserService userService;

    @RequestMapping("/grouperList")
    @LoggerManage(description = "组长列表")
    public String grouperList(Model model){
        List<Grouper> groupers = grouperService.findAll();
        model.addAttribute("groupers",groupers);
        return "admin/grouper_list";
    }

    @RequestMapping("/addHtml/{type}")
    @LoggerManage(description = "增加组长页面")
    public String addHtml(@PathVariable Integer type, @RequestParam(value = "value") String value, Model model){
        List<User> usersLoad = null;
        if (type == 1){
//            按工号
            usersLoad= userService.findAllByJobNum(value);
        }else if (type == 2){
//            按姓名
            usersLoad = userService.findAllByName(value);
        }
        model.addAttribute("usersLoad",usersLoad);
        return "admin/grouper_load";
    }

    @ResponseBody
    @RequestMapping("/changeRank")
    @LoggerManage(description = "更改用户type")
    public Response changeRank(@RequestParam(value = "userId") String userId){
        Integer id = Integer.parseInt(userId);
        boolean b = grouperService.notGrouper(id);
        if (b){
            return result(ExceptionMsg.UserTypeSuccess);
        }
        return result(ExceptionMsg.UserTypeFailed);
    }

    @ResponseBody
    @RequestMapping("/changeCanLogin")
    @LoggerManage(description = "切换组长登陆权限")
    public Response changeCanLogin(@RequestParam(value = "id") String paramId){
        Integer id = Integer.parseInt(paramId);
        boolean b = grouperService.notCanLogin(id);
        if (b){
            return result(ExceptionMsg.ChangeCanLoginSuccess);
        }
        return result(ExceptionMsg.ChangeCanLoginFailed);
    }

    @ResponseBody
    @RequestMapping("/updatePwd")
    @LoggerManage(description = "重置组长密码")
    public Response updatePwd(@RequestParam(value = "grouperId") String grouperId,@RequestParam(value = "password") String password){
        Integer id = Integer.parseInt(grouperId);
        grouperService.updatePwd(password,id);
        return result(ExceptionMsg.ResetPwdSuccess);
    }

    @ResponseBody
    @RequestMapping(value = "/remove")
    @LoggerManage(description = "移除组长")
    public Response remove(HttpServletRequest request){
        String[] grouperIds = request.getParameterValues("id");
        Integer[] ids = DataUtils.turn(grouperIds);
        try {
            grouperService.remove(ids);
            return result(ExceptionMsg.GrouperRemoveSuccess);
        }catch (Exception e){
            return result(ExceptionMsg.GrouperRemoveFailed);
        }
    }
}
