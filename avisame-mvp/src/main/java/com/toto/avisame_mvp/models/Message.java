package com.toto.avisame_mvp.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.toto.avisame_mvp.utils.DateUtils;

/**
 * Created by Octavio on 03/12/2016.
 */
public class Message implements Parcelable, Comparable<Message> {

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

    protected Message(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.name = in.readString();
        this.message = in.readString();
        this.date = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.id);
        parcel.writeString(this.userId);
        parcel.writeString(this.name);
        parcel.writeString(this.message);
        parcel.writeString(this.date);
        parcel.writeString(this.latitude);
        parcel.writeString(this.longitude);
        parcel.writeString(this.type);
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
