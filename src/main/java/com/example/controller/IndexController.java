package com.example.controller;

import com.example.comm.aop.LoggerManage;
import com.example.comm.config.Access;
import com.example.comm.config.WebSecurityConfig;
import com.example.domain.bean.Login;
import com.example.domain.entity.Admin;
import com.example.domain.entity.Grouper;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Role;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.AdminService;
import com.example.service.GrouperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController extends BaseController{

    @Autowired
    private AdminService adminService;

    @Autowired
    private GrouperService grouperService;

    @RequestMapping("/admin/index")
    @Access(roles = {Role.admin})
    @LoggerManage(description = "管理员首页")
    public String indexAdmin(){
        return "admin/index";
    }

    @RequestMapping("/grouper/index")
    @LoggerManage(description = "组长首页")
    @Access(roles = {Role.grouper})
    public String indexGrouper(){
        return "grouper/index";
    }

    @RequestMapping("/")
    public String index(){
        return "redirect:/login";
    }

    @RequestMapping("/login")
    @LoggerManage(description = "登陆")
    public String login(){
        return "login";
    }

    /**
     * 登陆验证
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/loginPost")
    @LoggerManage(description = "登陆验证")
    public Response loginPost(HttpSession session, HttpServletRequest request){
        String jobNum = request.getParameter("jobNum");
        String password = request.getParameter("password");
        String loginType = request.getParameter("loginType");

        if ("0".equals(loginType)){
//        管理员 工号是否存在
            boolean exists = adminService.existsByJobNum(jobNum);
            if (!exists){
                return result(ExceptionMsg.LoginJobNumNotUerFailed);
            }
//           是否有数据
            List<Admin> admins = adminService.findAllByJobNumAndPassword(jobNum, password);
            if (admins.size() == 0){
                return result(ExceptionMsg.LoginPasswordFailed);
            }
//           查看登陆权限
            CanLogin canLogin = admins.get(0).getCanLogin();
            if (canLogin == null || canLogin == CanLogin.no){
                return result(ExceptionMsg.LoginCantFailed);
            }
            Login login = new Login();
            login.setJobNum(jobNum);
            login.setName(admins.get(0).getName());
            login.setRole(Role.admin);
            session.setAttribute(WebSecurityConfig.SESSION_KEY, login);
            return result("admin/index",ExceptionMsg.LoginSuccess);
        }else if("1".equals(loginType)){
//        组长 工号是否存在
            boolean exists = grouperService.existsByJobNum(jobNum);
            if (!exists){
                return result(ExceptionMsg.LoginJobNumNotUerFailed);
            }
//           是否有数据
            List<Grouper> groupers = grouperService.findAllByJobNumAndPassword(jobNum, password);
            if (groupers.size() == 0){
                return result(ExceptionMsg.LoginPasswordFailed);
            }
//           查看登陆权限
            CanLogin canLogin = groupers.get(0).getCanLogin();
            if (canLogin == null || canLogin == CanLogin.no){
                return result(ExceptionMsg.LoginCantFailed);
            }
            Login login = new Login();
            login.setJobNum(jobNum);
            login.setName(groupers.get(0).getUser().getName());
            login.setRole(Role.grouper);
            session.setAttribute(WebSecurityConfig.SESSION_KEY, login);
            return result("grouper/index",ExceptionMsg.LoginSuccess);

        }

        return result(ExceptionMsg.FAILED);
    }

    @RequestMapping("/logout")
    @LoggerManage(description = "注销")
    public String logout(HttpSession session){
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }

    /**
     * 无权限页面
     * 范围：url请求
     * @return
     */
    @RequestMapping("/noAccess")
    public String noAccess(){
        return "noAccess";
    }

    /**
     * 无权限提示
     * 范围：ajax提交后，提示框显示
     * @return
     */
    @ResponseBody
    @RequestMapping("/noAccessAjax")
    public Response noAccessAjax(){
        return result(ExceptionMsg.RoleNoAccess);
    }

    @ResponseBody
    @RequestMapping("/hello")
    @Access(roles = {Role.admin})
    public String hello(){
        return "this is hello";
    }

}
