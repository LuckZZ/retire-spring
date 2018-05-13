package com.example.serviceImpl;

import com.example.service.MailContentBuilder;
import com.example.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Create by : Zhangxuemeng
 */
@Service
public class MailServiceImpl implements MailService{

    private static final boolean ISHTML = true;
    private static final String encoding = "UTF-8";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailContentBuilder contentBuilder;

    @Value("${mail.from}")
    private String from;

/*    @Override
    public void prepareAndSend(String recipient, String message) throws Exception {
        //这个是javax.mail.internet.MimeMessage下的
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        //        基本设置
        helper.setFrom(from);//发送者.
        helper.setTo(recipient);//接收者.
        helper.setSubject("测试邮件（邮件主题）");//邮件主题.
        helper.setText(message);
        mailSender.send(mimeMessage);
    }*/

    @Override
    public void sendMail(String recipient, String subject, String templateName, Map<String, Object> datas){
        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, encoding);
                messageHelper.setFrom(from);
                messageHelper.setTo(recipient);
                messageHelper.setSubject(subject);
                messageHelper.setText(contentBuilder.buildMessage(templateName, datas), ISHTML);
            }
        };
        mailSender.send(mimeMessagePreparator);
    }
}
