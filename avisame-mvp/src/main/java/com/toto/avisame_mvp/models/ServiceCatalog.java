package com.toto.avisame_mvp.models;

import com.toto.avisame_mvp.BuildConfig;

public class ServiceCatalog {

    //Token
    public static final String TOKEN_KEY = "Bearer ";

    public static final String BASE_API_URL = BuildConfig.BASE_API_URL + "api";
    public static final String USERS = "/users";

    public static final String LOGIN = "/login";

    public static final String ARRIVED = "/arrivals";
    public static final String ALERT = "/alerts";
    public static final String DANGER = "/dangers";
}
