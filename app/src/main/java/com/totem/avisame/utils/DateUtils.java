package com.totem.avisame.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatToString(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        String format = null;

        try{
            format = sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return format;
    }
}
