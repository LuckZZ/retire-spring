package com.example.comm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
    /**
     * 登陆session key
     */
    public final static String SESSION_KEY = "login_user";

    @Bean
    public SecurityInterceptor getSecurityInterceptor(){
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

//       排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/logout");
        addInterceptor.excludePathPatterns("/mail/verify**");
        addInterceptor.excludePathPatterns("/mail/sendPwdEmail");
        addInterceptor.excludePathPatterns("/forgetView");
        addInterceptor.excludePathPatterns("/");

//      拦截配置
        addInterceptor.addPathPatterns("/**");
    }
}
