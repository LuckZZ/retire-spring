package com.example.utils;

import java.security.MessageDigest;

/**
 * Create by : Zhangxuemeng
 */
public class MD5Util {

    public static String encrypt(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
//          把byte转化为16进制字符,toHexString转化为无符号16进制的字符串
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}