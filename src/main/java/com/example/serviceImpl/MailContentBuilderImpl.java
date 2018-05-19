package com.example.serviceImpl;

import com.example.service.MailContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class MailContentBuilderImpl implements MailContentBuilder{

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public String buildMessage(String templateName, Map<String, Object> datas) {
        Context context = new Context();
        for (Map.Entry<String, Object> entry : datas.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        return templateEngine.process(templateName, context);
    }
}
