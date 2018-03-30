package com.example.utils;

import java.util.Arrays;
import java.util.List;

public class DataUtils {

    /**
     * String类型的数组转换为Integer类型的数组
     * @param strs
     * @return
     */
    public static Integer[] turn(String[] strs){
        Integer[] ints = new Integer[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = new Integer(strs[i]);
        }
        return ints;
    }

    /**
     * 把包含“，”或“,”的字符串切割成list
     * @param str
     * @return
     */
    public static List<String> turnToList(String str){
        String[] s = str.split("，|\\,");
        List<String> list = Arrays.asList(s);
        return list;
    }
}
