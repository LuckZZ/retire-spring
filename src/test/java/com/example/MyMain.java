package com.example;

import com.example.utils.MD5Util;

public class MyMain {

    public static void main(String[] args) {
        String str = "123456";
        String s2 = MD5Util.encrypt(str);
        System.out.println(s2);
    }
}