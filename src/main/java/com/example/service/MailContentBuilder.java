package com.example.service;

import java.util.Map;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public interface MailContentBuilder {

    String buildMessage(String templateName, Map<String, Object> datas);
}
