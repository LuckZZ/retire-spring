package com.example;

import com.example.domain.enums.CanLogin;
import com.example.utils.DateUtils;

public class MyMain {
    public static void main(String[] args) {
/*        long time = DateUtils.getCurrentTime();
        System.out.println(time);
        String data = DateUtils.getDateSequence();
        System.out.println(data);*/
        CanLogin canLogin = CanLogin.yes;
        System.out.println(canLogin);
        System.out.println(canLogin.toString());
    }
}
