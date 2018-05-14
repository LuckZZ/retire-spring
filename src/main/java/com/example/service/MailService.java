package com.example.service;

import com.example.domain.bean.Login;
import com.example.domain.enums.Role;

/**
 * Create by : Zhangxuemeng
 */
public interface MailService {

    /*发送验证邮箱信息*/
    void verifyEmail(Login login);

    /*验证邮箱*/
    boolean verify(Role role, String verifyCode);
}
