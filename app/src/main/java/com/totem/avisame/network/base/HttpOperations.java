package com.totem.avisame.network.base;

import com.android.volley.Request;

public enum HttpOperations {

    GET(Request.Method.GET),
    POST(Request.Method.POST),
    PUT(Request.Method.PUT),
    DELETE(Request.Method.DELETE);

    private int volleyMethod;

    HttpOperations(int numVal) {
        this.volleyMethod = numVal;
    }

    public int getVal() {
        return volleyMethod;
    }
}
