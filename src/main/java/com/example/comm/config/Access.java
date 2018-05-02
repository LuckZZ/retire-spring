package com.example.comm.config;

import com.example.domain.enums.Role;

import java.lang.annotation.*;

/**
 * Create by : Zhangxuemeng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Access {

    Role[] roles() default {};

}
