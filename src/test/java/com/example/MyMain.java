package com.example;

import com.example.comm.Constant;
import com.example.utils.MD5Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MyMain {

    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add(""+i);
        }

        String[] array= list.toArray(new String[list.size()]);

    }
}