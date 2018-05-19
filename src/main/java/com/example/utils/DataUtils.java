package com.example.utils;

import com.example.comm.Constant;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
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
     * 把活动自定义输入框的内容分割
     * @param str
     * @return
     */
    public static List<String> strToList(String str){
        if (str == null){
            return null;
        }
        String[] strArray = str.split(";|；");
        List<String> list = Arrays.asList(strArray);
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

    //生成length位随机数字和字母
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    //生成length位随机数字
    public static String getNumberRandom(int length) {
        Random random = new Random();
        String result="";
        for (int i = 0; i < length; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * 密码加密后，字符串
     * @param password
     * @return
     */
    public static String getPasswordMD5(String password){
        String str = MD5Util.encrypt(password+ Constant.PASSWORD_SALT);
        return str;
    }
}