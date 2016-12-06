package com.totem.avisame.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.totem.avisame.models.User;

import java.util.List;

public class AppSettings {

    public static final String TOKEN_HEADER = "user-token";
    private static Context sContext;

    public AppSettings() {
    }

    public static void init(Context ctx) {
        sContext = ctx;
    }

    public static void destroy() {
        sContext = null;
    }

    /**
     * Clear app data. Used where user is logged out.
     * WARNING, RESETS ALL THE SHARED PREFERENCES
     */
    public static void cleanSettings() {

        sContext.getSharedPreferences(KeysSetting.AVISAME_SETTINGS.name(), Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }


    private static SharedPreferences getSharedPreferences() {

        return sContext.getSharedPreferences(KeysSetting.AVISAME_SETTINGS.name(), Context.MODE_PRIVATE);
    }

    public static void configureLocationManager(){


    }

    public static void setPushTokenValue(String value) {
        getSharedPreferences().edit().putString(KeysSetting.PUSH_TOKEN_VALUE.name(), value).apply();
    }

    public static String getPushTokenValue() {
        return getSharedPreferences().getString(KeysSetting.PUSH_TOKEN_VALUE.name(), null);
    }

    public static void setTokenValue(String value) {
        getSharedPreferences().edit().putString(KeysSetting.TOKEN_VALUE.name(), value).apply();
    }

    public static String getTokenValue() {
        return getSharedPreferences().getString(KeysSetting.TOKEN_VALUE.name(), null);
    }

//    public static void setUserData(User user) {
//        String jsonUser = AppUtils.getJsonParser().toJson(user);
//        getSharedPreferences().edit().putString(KeysValues.USER_DATA.name(), jsonUser).commit();
//    }
//
//    public static User getUser() {
//        String jsonUser = getSharedPreferences().getString(KeysValues.USER_DATA.name(), null);
//        return AppUtils.getJsonParser().fromJson(jsonUser, User.class);
//    }

//    private static User setCountryURL(User user) {
//        if (user != null && user.getCountry() != null) {
//            user.setURLCountry(user.getCountry());
//        }
//        return user;
//    }

    public static void setUserData(User user) {
        String jsonUser = new Gson().toJson(user);
        getSharedPreferences().edit().putString(KeysValues.USER_DATA.name(), jsonUser).commit();
    }


    public static User getUser() {
        String jsonUser = getSharedPreferences().getString(KeysValues.USER_DATA.name(), null);
        return new Gson().fromJson(jsonUser, User.class);
    }

//    public static boolean isLoggedIn() {
//        return getUser() != null && getUser().getToken() != null;
//    }

    enum KeysSetting {
        AVISAME_SETTINGS,
        TOKEN_VALUE,
        PUSH_TOKEN_VALUE
    }

    enum KeysValues {
        USER_DATA
    }

}
