package com.example.serviceImpl;

import com.example.domain.bean.Login;
import com.example.domain.entity.Admin;
import com.example.domain.entity.Grouper;
import com.example.domain.enums.Role;
import com.example.domain.enums.Verify;
import com.example.service.AdminService;
import com.example.service.GrouperService;
import com.example.service.MailContentBuilder;
import com.example.service.MailService;
import com.example.utils.DataUtils;
import com.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private AdminService adminService;

    @Autowired
    private GrouperService grouperService;

    @Override
    public void sendVerifyEmail(Login login) {
        //        当前时间
        long currentTime = DateUtils.getCurrentTime();
//        当前时间加一个小时
        String passVTime = DateUtils.timeStampToFormat(currentTime + 60*60*1000);
//        8位随机验证码
        String strRandom = DataUtils.getStringRandom(8);
        if (login.getRole() == Role.admin){
            //        链接
            String strUrl = "http://localhost:8080/mail/verify?type=0&verifyCode="+strRandom;
            Admin admin = adminService.findOne(login.getId());
//           修改数据库验证码和时间
            adminService.updateVerifyCode(strRandom, String.valueOf(currentTime), admin.getAdminId());
            Map map = new HashMap();
            map.put("paramName",admin.getName());
            map.put("paramUrl",strUrl);
            map.put("paramTime",String.valueOf(passVTime));
//            发送验证邮件
            sendMail(admin.getEmail(), "[离退休部]验证邮箱通知！","mail/mailVerifyTemplate",map);
        }else if(login.getRole() == Role.grouper){
            String strUrl = "http://localhost:8080/mail/verify?type=1&verifyCode="+strRandom;
            Grouper grouper = grouperService.findOne(login.getId());
            //           修改数据库验证码和时间
            grouperService.updateVerifyCode(strRandom, String.valueOf(currentTime), grouper.getGrouperId());
            Map map = new HashMap();
            map.put("paramName",grouper.getUser().getName());
            map.put("paramUrl",strUrl);
            map.put("paramTime",passVTime);
            sendMail(grouper.getEmail(), "[离退休部]验证邮箱通知！","mail/mailVerifyTemplate",map);
        }
    }

    @Override
    public boolean verify(Role role, String verifyCode) {
        long currentTime = DateUtils.getCurrentTime();
//        当前时间减去一个小时
        currentTime-=60*60*1000;
        if (role == Role.admin){
//            管理员
            List<Admin> admins = adminService.findAll();
            for (Admin admin : admins) {
                if (verifyCode.equals(admin.getVerifyCode())&&(Long.parseLong(admin.getVerifyTime())>currentTime)){
                    adminService.updateVerify(Verify.yes, admin.getAdminId());
                    return true;
                }
            }

        }else if(role == Role.grouper){
            //            组长
            List<Grouper> groupers = grouperService.findAll();
            for (Grouper grouper : groupers) {
                if (verifyCode.equals(grouper.getVerifyCode())&&(Long.parseLong(grouper.getVerifyTime())>currentTime)){
                    grouperService.updateVerify(Verify.yes, grouper.getGrouperId());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void sendPwdEmail(Admin admin) {
//        6位随机验证码
        String strRandom = DataUtils.getNumberRandom(6);
//           修改密码
        adminService.updatePassword(DataUtils.getPasswordMD5(strRandom), admin.getAdminId());
        Map map = new HashMap();
        map.put("paramName",admin.getName());
        map.put("paramPwd",strRandom);
//            发送验证邮件
        sendMail(admin.getEmail(), "[离退休部]重置密码通知！","mail/mailPwdTemplate",map);
    }

    @Override
    public void sendPwdEmail(Grouper grouper) {
//        6位随机验证码
        String strRandom = DataUtils.getNumberRandom(6);
//           修改密码
        grouperService.updatePassword(DataUtils.getPasswordMD5(strRandom), grouper.getGrouperId());
        Map map = new HashMap();
        map.put("paramName",grouper.getUser().getName());
        map.put("paramPwd",strRandom);
//            发送验证邮件
        sendMail(grouper.getEmail(), "[离退休部]重置密码通知！","mail/mailPwdTemplate",map);
    }

    private void sendMail(String recipient, String subject, String templateName, Map<String, Object> datas){
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
