package com.toto.avisame_mvp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Octavio on 03/12/2016.
 */
public class User implements Serializable {

    @SerializedName("_id")
    private String id;
    @SerializedName("token")
    private String token;
    @SerializedName("pushToken")
    private String pushToken;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("dni")
    private String dni;

    private List<String> friends;

    public User(String id, String token, String pushToken, String email, String password, String firstName, String lastName, String dni) {
        this.id = id;
        this.token = token;
        this.pushToken = pushToken;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getPushToken() {
        return pushToken;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDni() {
        return dni;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
