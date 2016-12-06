package com.totem.avisame.network;

public class ErrorHandler {

    public static String getMessageRequestCode(String error) {

        switch (error) {
            case "409":
                return "The user is already created";
            case "401":
                return "Please enter a valid email and password combination";
            default:
                return "Conection error";
        }
    }
}
