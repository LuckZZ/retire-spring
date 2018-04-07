package com.example;

import com.example.domain.enums.CanLogin;
import com.example.utils.DataUtils;
import com.example.utils.DateUtils;

import java.util.List;

public class MyMain {
    public static void main(String[] args) {
        String[] strings = {"a;b","c;d","e;f","g;h;k"};
        String[][] strings1 = DataUtils.oneStrToTwoStr(strings);
        for (int i = 0; i < strings1.length; i++) {
            System.out.println(strings1[i].length);
            for (int j = 0; j < strings1[i].length; j++) {
                System.out.println(strings1[i][j]);
            }
            System.out.println("- - - - - -");
        }

    }
}
