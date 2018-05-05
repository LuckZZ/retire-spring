package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 得到当前时间戳
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 得到格式化后的时间
     * @return
     */
    public static String getCuttentFormatTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 时间戳转换为格式后的时间
     * @param timeStamp
     * @return
     */
    public static String timeStampToFormat(String timeStamp){
        Date date = new Date(Long.parseLong(String.valueOf(timeStamp)));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public static String timeStampToFormat(Long timeStamp){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(timeStamp));
    }

}
