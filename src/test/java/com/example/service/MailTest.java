package com.example.service;

import com.example.RetireSpringApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.Configuration;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by : Zhangxuemeng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RetireSpringApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MailTest {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailService mailService;

    /**
     * 修改application.properties的用户，才能发送。
     */
    @Test
    public void sendSimpleEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("i_xm_zhang@163.com");//发送者.
        message.setTo("529055293@qq.com");//接收者.
        message.setSubject("测试邮件（邮件主题）");//邮件主题.
        message.setText("这是邮件内容");//邮件内容.
        mailSender.send(message);//发送邮件
    }

    @Test
    public void sendSimpleEmail2(){
        Map map = new HashMap();
        map.put("message","这是message");
        try {
            mailService.sendMail("529055293@qq.com","测试邮件（邮件主题）", "mail/mailTemplate", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
