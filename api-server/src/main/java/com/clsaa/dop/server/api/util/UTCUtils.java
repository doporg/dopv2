package com.clsaa.dop.server.api.util;


import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class UTCUtils {

    public static Date utcToLocal(long utcTime){
        return new Date(utcTime* 1000);
    }

    public static Date utcMsToLocal(long utcTime){
        return new Date(utcTime);
    }

    public static Date stringToDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            return sdf.parse(dateStr);
        }catch (ParseException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
