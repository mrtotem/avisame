package com.totem.avisame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Octavio on 03/12/2016.
 */
public class Message implements Serializable {

    @SerializedName("_id")
    private String id;
    @SerializedName("userId")
    private String userId;
    @SerializedName("message")
    private String message;
    @SerializedName("date")
    private String date;
    @SerializedName("lat")
    private String latitude;
    @SerializedName("long")
    private String longitude;

    public Message(String id, String userId, String message, String date, String latitude, String longitude) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
