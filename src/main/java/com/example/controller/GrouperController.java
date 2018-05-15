package com.example.controller;

import com.example.comm.Constant;
import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.comm.config.WebSecurityConfig;
import com.example.domain.bean.CommSearch;
import com.example.domain.bean.Login;
import com.example.domain.entity.Grouper;
import com.example.domain.entity.User;
import com.example.domain.enums.Role;
import com.example.domain.enums.Verify;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.GrouperService;
import com.example.service.UserService;
import com.example.utils.DataUtils;
import com.example.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/grouper")
@Controller
@Access(roles = Role.admin)
public class GrouperController extends BaseController{
    @Autowired
    private GrouperService grouperService;

    @Autowired
    private UserService userService;

    @RequestMapping("/grouperList")
    @LoggerManage(description = "组长列表")
    public String grouperList(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("userCommSearch", new CommSearch(1, ""));
        Page<Grouper> datas = grouperService.findAllNoCriteria(page);
        model.addAttribute("datas",datas);
        return "admin/grouper_list";
    }

    /**
     *
     * @param model
     * @param type 0：根据工号查询；1：根据姓名查询
     * @param value
     * @return
     */
    @RequestMapping("/grouperList/{type}/{value}")
    @LoggerManage(description = "组长列表BySearch")
    public String grouperListByType(Model model, @PathVariable Integer type, @PathVariable String value, @RequestParam(value = "page", defaultValue = "0") Integer page){
        model.addAttribute("userCommSearch", new CommSearch(type, value));
        if (type == 1 && value != null){
//        根据工号
            Page<Grouper> datas = grouperService.findAllByJobNum(value,page);
            model.addAttribute("datas",datas);
            return "admin/grouper_list";
        }else if (type == 2 && value != null){
//        根据姓名
            Page<Grouper> datas = grouperService.findAllByName(value,page);
            model.addAttribute("datas",datas);
            return "admin/grouper_list";
        }
//        重定向
        return "redirect:/grouper/grouperList";
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
        grouperService.updatePassword(getPasswordMD5(password),id);
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

    @RequestMapping(value = "/pwdUpdateView")
    @LoggerManage(description = "修改组长密码界面")
    @Access(roles = Role.grouper)
    public String pwdUpdateView(Model model){
        model.addAttribute("grouper",new Grouper());
        return "grouper/pwd_update";
    }

    @ResponseBody
    @RequestMapping(value = "/pwdUpdate")
    @LoggerManage(description = "修改组长密码")
    @Access(roles = Role.grouper)
    public Response pwdUpdate(HttpServletRequest request){
        String oldPassword = request.getParameter("oldPassword");
        String password = request.getParameter("password");
        //     取出session
        HttpSession session = request.getSession();
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Integer grouperId = login.getId();
        Grouper grouper = grouperService.findOne(grouperId);
        if (!grouper.getPassword().equals(getPasswordMD5(oldPassword))){
            return result(ExceptionMsg.LoginPasswordFailed);
        }
        grouperService.updatePassword(getPasswordMD5(password), grouperId);
        return result(ExceptionMsg.pwdUpdateSuccess);
    }

    @RequestMapping(value = "/emailUpdateView")
    @LoggerManage(description = "修改邮箱界面")
    @Access(roles = Role.grouper)
    public String emailUpdateView(Model model, HttpSession session){
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Grouper grouper = grouperService.findOne(login.getId());
        model.addAttribute("grouper",grouper);
        return "grouper/mail_update";
    }

    @RequestMapping(value = "/emailAddView")
    @LoggerManage(description = "新增邮箱界面")
    @Access(roles = Role.grouper)
    public String emailAddView(){
        return "grouper/mail_add";
    }

    @ResponseBody
    @RequestMapping(value = "/emailUpdate")
    @LoggerManage(description = "修改邮箱")
    @Access(roles = Role.grouper)
    public Response emailUpdate(HttpServletRequest request){
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        Integer adminId = login.getId();

        grouperService.updateEmail(email, Verify.no, adminId);
        return result(ExceptionMsg.EmailUpdSuccess);
    }

    /**
     * 密码加密后，字符串
     * @param password
     * @return
     */
    private String getPasswordMD5(String password){
        String str = MD5Util.encrypt(password+ Constant.PASSWORD_SALT);
        return str;
    }

}
