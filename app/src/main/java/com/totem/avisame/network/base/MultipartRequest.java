package com.totem.avisame.network.base;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by @ppatarca on 16/07/15.
 * This class is a multipart request implementation to provide multipart request in volley library.
 */
public class MultipartRequest extends Request<String> {

    public static final String KEY_DATA_FORM_JSON = "data";
    public static final String LOG_VOLLEY = "VOLLEY";

    public static final String MIME_TYPE_IMAGE_JPEG = "image/jpeg";

    private MultipartEntityBuilder mEntity;
    private HttpEntity mHttpEntity;
    private final Listener<String> mListener;
    private HashMap<String, Object> mFieldsRequest;

    /**
     * This constructor is used to send a multipart request using form fields only
     *
     * @param method int - HTTP method
     * @param url String - URI
     * @param listener Success callback
     * @param errorListener - Error callback
     * @param fieldsRequest map of Objects data
     */
    public MultipartRequest(int method, String url, HashMap<String, Object> fieldsRequest,
                            Listener<String> listener, ErrorListener errorListener) {
        super(method, url, errorListener);

        mListener = listener;
        mFieldsRequest = fieldsRequest;
        buildMultipartEntity();
    }


    private void buildMultipartEntity() {

        mEntity = MultipartEntityBuilder.create();

        // Added form fields
        if (mFieldsRequest != null){
            for (String key : mFieldsRequest.keySet()){
                Object value = mFieldsRequest.get(key);
                if (value instanceof String)
                    mEntity.addTextBody(key,(String) value);
                else if (value instanceof File) {
                    // Add files like jpeg
                    File file = (File) value;
                    mEntity.addBinaryBody(key, file,
                            ContentType.create(MIME_TYPE_IMAGE_JPEG), file.getName());
                } else if (value instanceof JSONObject){
                    JSONObject jsonObject = (JSONObject) value;
                    mEntity.addTextBody(KEY_DATA_FORM_JSON, jsonObject.toString(), ContentType.APPLICATION_JSON);
                } else {
                    mEntity.addTextBody(key, value.toString());
                }
            }
        }

        mHttpEntity = mEntity.build();
    }

    @Override
    public String getBodyContentType(){
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_VOLLEY, "IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String decodedResponse = "{}";
        try {
            decodedResponse = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(decodedResponse, getCacheEntry());
    }

//    @Override
//    protected Map<String,String> getParams(){
//        Map<String,String> params = new HashMap<String, String>();
//        params.put("user",userAccount.getUsername());
//        params.put("pass",userAccount.getPassword());
//        params.put("comment", Uri.encode(comment));
//        params.put("comment_post_ID",String.valueOf(postId));
//        params.put("blogId",String.valueOf(blogId));
//        return params;
//    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
