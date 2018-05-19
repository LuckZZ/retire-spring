package com.example.comm.config;

import com.example.domain.enums.Role;

import java.lang.annotation.*;

/**
 * 此注解用在类上和方法上
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Access {

    Role[] roles() default {};

}
