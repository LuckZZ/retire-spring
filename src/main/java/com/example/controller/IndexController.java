package com.example.controller;

import com.example.comm.WebSecurityConfig;
import com.example.comm.aop.LoggerManage;
import com.example.domain.bean.Login;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController extends BaseController{

    @RequestMapping("/admin/index")
    @LoggerManage(description = "管理员首页")
    public String indexAdmin(){
        return "admin/index";
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
//        管理员
            if ("123456".equals(jobNum)){
                Login login = new Login();
                login.setJobNum(jobNum);
                login.setPassword(password);
                login.setLoginType(loginType);
                session.setAttribute(WebSecurityConfig.SESSION_KEY, login);
                return result("admin/index",ExceptionMsg.LoginSuccess);
            }
        }else if("1".equals(loginType)){
//            组长
            if ("123456".equals(jobNum)){
                Login login = new Login();
                login.setJobNum(jobNum);
                login.setPassword(password);
                login.setLoginType(loginType);
                session.setAttribute(WebSecurityConfig.SESSION_KEY, login);
                return result("admin/index",ExceptionMsg.LoginSuccess);
            }
        }

        return result(ExceptionMsg.LoginPasswordFailed);
    }

    @RequestMapping("/logout")
    @LoggerManage(description = "注销")
    public String logout(HttpSession session){
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }

}
