package com.totem.avisame.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Octavio on 03/12/2016.
 */
public class User implements Serializable {

    @SerializedName("_id")
    private String id;
    @SerializedName("token")
    private String token;
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

    public User(String id,String token , String email, String password, String firstName, String lastName, String dni) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
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
}
