package com.example.controller;

import com.example.domain.result.ExceptionMsg;
import com.example.domain.result.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Controller
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 返回操作状态
     * @param msg
     * @return
     */
    protected Response result(ExceptionMsg msg){
        return new Response(msg);
    }

    /**
     * 返回数据、操作状态
     * @param data
     * @param msg
     * @return
     */
    protected Response result(Object data, ExceptionMsg msg){
        return new Response(data, msg);
    }

}
