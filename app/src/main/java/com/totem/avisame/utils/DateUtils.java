package com.totem.avisame.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatToString(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm aa");
        String format = null;

        try {
            format = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    public static Date formatToDate(String value) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm aa");
        Date date = null;

        try {
            date = sdf.parse(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
