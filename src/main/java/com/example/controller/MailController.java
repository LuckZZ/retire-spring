package com.example.controller;

import com.example.comm.config.Access;
import com.example.comm.config.WebSecurityConfig;
import com.example.domain.bean.Login;
import com.example.domain.entity.Admin;
import com.example.domain.entity.Grouper;
import com.example.domain.enums.Role;
import com.example.domain.enums.Verify;
import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import com.example.service.AdminService;
import com.example.service.GrouperService;
import com.example.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Create by : Zhangxuemeng
 */

@Controller
@RequestMapping("/mail")
@Access(roles = Role.admin)
public class MailController extends BaseController {
    @Autowired
    private MailService mailService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private GrouperService grouperService;

    /**
     * 验证邮箱
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendEmail")
    public Response sendEmail(HttpSession session) {
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        if (login.getRole() == Role.admin){
            Admin admin = adminService.findOne(login.getId());
            Verify verify = admin.getVerify();
            if (verify == Verify.yes){
                return result(ExceptionMsg.EmailSendForVerifyFailed);
            }

        }else if(login.getRole() == Role.grouper){
            Grouper grouper = grouperService.findOne(login.getId());
            Verify verify = grouper.getVerify();
            if (verify == Verify.yes){
                return result(ExceptionMsg.EmailSendForVerifyFailed);
            }
        }
        mailService.verifyEmail(login);
        return result(ExceptionMsg.EmailSendSuccess);
    }

    @RequestMapping("/verify")
    public String verify(HttpServletRequest request, Model model) {
        Integer type = Integer.parseInt(request.getParameter("type"));
        String verifyCode = request.getParameter("verifyCode");
        boolean b = false;
        if (type == 0){
            b = mailService.verify(Role.admin, verifyCode);
        }else if(type == 1){
            b = mailService.verify(Role.grouper, verifyCode);
        }
        if (b == true){
            model.addAttribute("message","邮箱验证成功");
            return "mail/verifyStatus";
        }
        model.addAttribute("message","邮箱验证失败");
        return "mail/verifyStatus";
    }

}
