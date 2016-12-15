package com.totem.avisame.utils;

import com.totem.avisame.models.DangerResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatToString(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        String format = null;

        try {
            format = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    public static Date formatToDate(String value) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        Date date = null;

        try {
            date = sdf.parse(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
