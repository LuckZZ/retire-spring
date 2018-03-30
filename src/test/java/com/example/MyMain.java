package com.example;

import com.example.domain.enums.CanLogin;
import com.example.utils.DataUtils;
import com.example.utils.DateUtils;

import java.util.List;

public class MyMain {
    public static void main(String[] args) {
//        DataUtils.
        String s = "1234567,1234567,1234567";
        List<String> list =  DataUtils.turnToList(s);
        for (String str : list){
            System.out.println(str);
        }
    }
}
