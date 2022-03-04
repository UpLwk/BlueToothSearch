package com.yeqifu.bus.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatTime {
    public static String format(String time){
        if (time == null){
            Date date = new Date(System.currentTimeMillis());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = dateFormat.format(date);
            return format;
        }

        String f0009 = "2021-12-09T15:42:13.500Z";
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;//拿到Date对象
        try {
            date = sdf.parse(f0009);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        str = sdf2.format(date);//输出格式：2018-10-23 08:13:24
        return str;
    }
}
