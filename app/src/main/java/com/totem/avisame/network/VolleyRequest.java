package com.totem.avisame.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

public abstract class VolleyRequest<T> {

    protected Context mContext;

    private JsonObjectRequest mRequest;

    public VolleyRequest(Context context) {

        mContext = context;
    }

    protected abstract void responseHandler(JSONObject json);

    protected abstract void errorHandler(VolleyError error);

    protected void onServiceSuccess(T response) {

    }

    protected void onServiceError() {

    }

    protected void setupRequest(String url, Integer method, JSONObject body, final HashMap<String, String> headers) {

        mRequest = new JsonObjectRequest(method, url, body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d("BaseVolleyResponse", "Response: " + response.toString());
                responseHandler(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                errorHandler(error);
            }
        }) {

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                if (headers != null)
//                    params.putAll(headers);
//
//                return params;
//            }
        };
    }

    public void executeVolleyRequest() {

        VolleySingleton
                .getInstance(mContext)
                .getRequestQueue()
                .add(mRequest);
    }
}