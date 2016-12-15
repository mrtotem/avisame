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

    private Date formatDate;

    public Message(String id, String userId, String message, String date, String latitude, String longitude, String type) {
        this.id = id;
        this.userId = userId;
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

    public Date getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = DateUtils.formatToDate(formatDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int compareTo(@NonNull Message message) {
        return Long.compare(this.formatDate.getTime(), message.formatDate.getTime());
    }
}
