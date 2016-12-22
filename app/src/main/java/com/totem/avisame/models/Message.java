package com.totem.avisame.models;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;
import com.totem.avisame.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Octavio on 03/12/2016.
 */
public class Message implements Serializable, Comparable<Message> {

    @SerializedName("_id")
    private String id;
    @SerializedName("userId")
    private String userId;
    @SerializedName("name")
    private String name;
    @SerializedName("message")
    private String message;
    @SerializedName("date")
    private String date;
    @SerializedName("lat")
    private String latitude;
    @SerializedName("long")
    private String longitude;
    @SerializedName("type")
    private String type;

    public Message(String id, String userId, String name, String message, String date, String latitude, String longitude, String type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
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

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(@NonNull Message message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Long.compare(DateUtils.formatToDate(message.getDate()).getTime(), DateUtils.formatToDate(this.date).getTime());
        }
        return 0;
    }
}
