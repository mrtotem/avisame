package com.totem.avisame.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

public class NetworkManager {

    private static NetworkManager mInstance = null;
    private RequestQueue mRequestQueue;

    private NetworkManager() {
        //Empty constructor
    }

    public static NetworkManager getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkManager();
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public void addRequest(Context ctx, JsonRequest request) {
        setUpAndAddRequest(ctx, request, false);
    }

    public void addRequest(Context ctx, Request request, boolean useCache) {
        setUpAndAddRequest(ctx, request, useCache);
    }

    private void setUpAndAddRequest(Context ctx, Request request, boolean useCache) {
        request.setShouldCache(useCache);
        request.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue(ctx).add(request);
    }

    public void cancelRequest(Context context, Object tag) {
        getRequestQueue(context).cancelAll(tag);
    }
}