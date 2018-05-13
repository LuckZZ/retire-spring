package com.example.service;

import java.util.Map;

/**
 * Create by : Zhangxuemeng
 */
public interface MailContentBuilder {

    String buildMessage(String templateName, Map<String, Object> datas);
}
