package com.example.service;

import java.util.Map;

/**
 * Create by : Zhangxuemeng
 */
public interface MailService {

//    void prepareAndSend(String recipient, String message) throws Exception;

    void sendMail(String recipient, String subject, String templateName, Map<String, Object> datas);
}
