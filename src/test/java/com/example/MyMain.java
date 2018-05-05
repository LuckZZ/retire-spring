package com.example;

import com.example.utils.DateUtils;

import java.util.Date;

public class MyMain {
    public static void main(String[] args) {
        System.out.println(DateUtils.timeStampToFormat("1525498102822"));
        System.out.println(new Date().getTime());
        System.out.println(DateUtils.getCurrentTime());
        System.out.println(DateUtils.getCuttentFormatTime());
    }
}