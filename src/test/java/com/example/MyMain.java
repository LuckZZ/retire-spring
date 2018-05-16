package com.example;

import com.example.comm.Constant;
import com.example.utils.MD5Util;

public class MyMain {

    public static void main(String[] args) {
        String str = "123456";
        String s2 = MD5Util.encrypt(str+ Constant.PASSWORD_SALT);
        System.out.println(s2);
    }
}