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

    /**
     * list转化为string，元素之间用“,”隔开。如：abcd，efgh
     * @param list
     * @return
     */
    public static String listToString(List<String> list){
        if (list == null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String string : list) {
            if (first){
                first=false;
            }else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * String[]转换String[][]
     * 用“;(英文分号)”或“；(中文分号)”分割分割字符串
     * @param strs
     * @return
     */
    public static String[][] oneStrToTwoStr(String[] strs){
        if (strs == null){
            return null;
        }
//        行
        String[][] strss = new String[strs.length][];
        for (int i = 0; i < strs.length; i++) {
            String[] strArray = strs[i].split(";|；");
//         列
            strss[i] = new String[strArray.length];
            for (int j = 0; j < strArray.length; j++) {
                strss[i][j] = strArray[j];
            }
        }
        return strss;
    }

}