package com.totem.avisame.network.base;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.JsonSyntaxException;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.network.NetworkManager;
import com.totem.avisame.network.ServiceCatalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class JSONWebServiceLoader<T> extends BaseWebServiceLoader<T> {

    protected boolean hasJSONResult = true;

    public JSONWebServiceLoader(Context ctx) {
        super(ctx);
    }

    protected void startServiceCall() {
        Request request;
        String fullPath = getBaseUrl() + getPath();
        Log.d(getClass().getSimpleName(), fullPath);

        HashMap<String, Object> hashMap = getMultipartData();

        if (hashMap != null) {
            request = buildMultipartRequest(getHTTPMethod().getVal(), fullPath, hashMap);
            for (Object object : hashMap.values())
                Log.d(getClass().getSimpleName(), object.toString());
        } else {
            JSONObject jsonObject = getRequestData();
            if (hasJSONResult) {
                request = buildRequest(getHTTPMethod().getVal(), fullPath, jsonObject);
            } else {
                request = buildSimpleRequest(getHTTPMethod().getVal(), fullPath, jsonObject);
            }

            if (jsonObject != null)
                Log.d(getClass().getSimpleName(), jsonObject.toString());
        }

        //Set the request tag to be the instance hashcode in order to be able to cancel it in the future.
        request.setTag(this.hashCode());
        NetworkManager.getInstance().addRequest(getContext(), request, mUseCache);
    }

    protected JsonRequest buildRequest(int method, String path, JSONObject obj) {

        return new JsonObjectRequest(method, path, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d(getClass().getSimpleName(), response.toString());
                Error error = checkForError(response);

                if (error == null) {
                    new AsyncTask<JSONObject, Void, T>() {
                        @Override
                        protected T doInBackground(JSONObject... params) {
                            try {
                                return processResponse(params[0]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(T processedResponse) {
                            super.onPostExecute(processedResponse);
                            deliverResult(wrapResponse(processedResponse));
                        }
                    }.execute(response);
                } else {
                    deliverResult(wrapError(error));
                }
            }
        }, getErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                try {
                    if (AppSettings.getUser() != null && AppSettings.getUser().getToken() != null) {
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", ServiceCatalog.TOKEN_KEY + AppSettings.getUser().getToken());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return headers;
            }
        };
    }

    protected Request buildSimpleRequest(int method, String path, final JSONObject obj) {

        return new Request<T>(method, path, getErrorListener()) {

            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {

                try {
                    String data = new String(response.data);
                    return (Response<T>) Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
                } catch (JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", ServiceCatalog.TOKEN_KEY + AppSettings.getUser().getToken());
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return obj.toString().getBytes();
            }

            @Override
            protected void deliverResponse(T response) {
                try {
                    T result = processResponse(new JSONObject());
                    deliverResult(wrapResponse(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected MultipartRequest buildMultipartRequest(int method, String path, HashMap<String, Object> obj) {
        return new MultipartRequest(method, path, obj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(getClass().getSimpleName(), response);

                Error error = null;
                try {
                    error = checkForError(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (error == null) {
                    new AsyncTask<String, Void, T>() {
                        @Override
                        protected T doInBackground(String... params) {
                            try {
                                return processResponse(new JSONObject(params[0]));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(T processedResponse) {
                            super.onPostExecute(processedResponse);
                            deliverResult(wrapResponse(processedResponse));
                        }
                    }.execute(response);
                } else {
                    deliverResult(wrapError(error));
                }
            }
        }, getErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                try {
                    if (AppSettings.getUser() != null && AppSettings.getUser().getToken() != null)
                        params.put("Authorization", ServiceCatalog.TOKEN_KEY + AppSettings.getUser().getToken());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
    }

    public boolean cancelLoad() {
        //Try to cancel the request by using the instance hashcode
//        NetworkManager.getInstance().cancelRequest(getContext(), this.hashCode());
        return true;
    }

    /**
     * Override to attach a non-empty object into the response
     *
     * @return
     */
    protected JSONObject getRequestData() {
        return null;
    }

    protected JSONArray getArrayRequestData() {
        return null;
    }

    /**
     * Override to attach a form fields into the response
     *
     * @return
     */
    protected HashMap<String, Object> getMultipartData() {
        return null;
    }

    /**
     * Process the response from the webService
     *
     * @param response
     * @return
     */
    protected abstract T processResponse(JSONObject response) throws JSONException;
}
