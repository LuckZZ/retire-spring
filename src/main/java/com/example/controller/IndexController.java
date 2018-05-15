package com.example.controller;

import com.example.comm.Constant;
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
import com.example.utils.DateUtils;
import com.example.utils.MD5Util;
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

    @RequestMapping("/forgetView")
    @LoggerManage(description = "忘记密码界面")
    public String forgetView(){
        return "forgetView";
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

//        加密后，密码
        password = getPasswordMD5(password);

        logger.info("正在登陆...  JobNum："+jobNum+" Role："+Role.values()[Integer.parseInt(loginType)].getName());

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
            Admin admin = admins.get(0);
//           查看登陆权限
            CanLogin canLogin = admin.getCanLogin();
            if (canLogin == null || canLogin == CanLogin.no){
                return result(ExceptionMsg.LoginCantFailed);
            }
//            更新登录时间
            adminService.updateLastTime(DateUtils.getCuttentFormatTime(),admin.getAdminId());

//            记录sesion
            Login login = new Login(admin.getAdminId(), jobNum, admin.getName(), admin.getImgUrl(), Role.admin);
            session.setAttribute(WebSecurityConfig.SESSION_KEY, login);

            logger.info("登陆成功...  JobNum："+jobNum+" Role："+Role.values()[Integer.parseInt(loginType)].getName());

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
            Grouper grouper = groupers.get(0);
//           查看登陆权限
            CanLogin canLogin = grouper.getCanLogin();
            if (canLogin == null || canLogin == CanLogin.no){
                return result(ExceptionMsg.LoginCantFailed);
            }
            //            更新登录时间
            grouperService.updateLastTime(DateUtils.getCuttentFormatTime(), grouper.getGrouperId());
//            记录session
            Login login = new Login(grouper.getGrouperId(), jobNum, grouper.getUser().getName(), grouper.getUser().getImgUrl(), Role.grouper, grouper.getUser().getGroup());
            session.setAttribute(WebSecurityConfig.SESSION_KEY, login);

            logger.info("登陆成功...  JobNum："+jobNum+" Role："+Role.values()[Integer.parseInt(loginType)].getName());

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
    @LoggerManage(description = "无权限页面")
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
    @LoggerManage(description = "无权限提示")
    public Response noAccessAjax(){
        return result(ExceptionMsg.RoleNoAccess);
    }

    @ResponseBody
    @RequestMapping("/hello")
    @Access(roles = {Role.admin})
    public String hello(){
        return "this is hello";
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
