package com.totem.avisame.network.base;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.network.ServiceCatalog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class JSONArrayWebServiceLoader<T> extends JSONWebServiceLoader<T> {

    public JSONArrayWebServiceLoader(Context ctx) {
        super(ctx);
    }

    protected JsonRequest buildRequest(int method, String path, final JSONObject obj) {

        return new JsonArrayRequest(method, path,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        deliverResult(wrapResponse(processResponse(response)));
                    }
                },
                getErrorListener()) {

            @Override
            public byte[] getBody() {
                return obj != null ? obj.toString().getBytes() : null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                try {
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", ServiceCatalog.TOKEN_KEY + AppSettings.getTokenValue());
                } catch (Exception e) {
                    Log.e("HEADERS ERRORS:", e.getLocalizedMessage());
                }

                return params;
            }
        };
    }

    /**
     * Do nothing, as it intends to override the behaviour.
     * Implement {@link JSONArrayWebServiceLoader#processResponse(JSONArray response)} instead
     */
    @Override
    protected final T processResponse(JSONObject response) {
        return null;
    }

    /**
     * Process the response from the webService
     *
     * @param response
     * @return
     */
    protected abstract T processResponse(JSONArray response);
}
