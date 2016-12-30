package com.toto.avisame_mvp.application;

public class TokenManager {

    private static TokenManager instance;

    private String token;

    public static TokenManager getInstance(){

        if(instance == null){

            instance = new TokenManager();
        }

        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
