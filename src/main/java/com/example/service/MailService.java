package com.example.service;

import com.example.domain.bean.Login;
import com.example.domain.entity.Admin;
import com.example.domain.entity.Grouper;
import com.example.domain.enums.Role;

/**
 * Create by : Zhangxuemeng
 */
public interface MailService {

    /*发送验证邮箱信息*/
    void sendVerifyEmail(Login login);

    /*验证邮箱*/
    boolean verify(Role role, String verifyCode);

    /*发送重置密码邮件信息*/
    void sendPwdEmail(Admin admin);

    void sendPwdEmail(Grouper grouper);
}
