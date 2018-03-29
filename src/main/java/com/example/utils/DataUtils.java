package com.example.utils;

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
}
