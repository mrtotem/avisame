package com.totem.avisame.network.loaders;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.models.Message;
import com.totem.avisame.network.ServiceCatalog;
import com.totem.avisame.network.base.HttpOperations;
import com.totem.avisame.network.base.JSONWebServiceLoader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Octavio on 03/12/2016.
 */
public class AlertMessageLoader extends JSONWebServiceLoader<Message> {

    private Bundle mArgs;
    private Context mContext;

    public AlertMessageLoader(Context ctx, Bundle args) {
        super(ctx);

        this.mContext = ctx;
        this.mArgs = args;
    }

    @Override
    protected Message processResponse(JSONObject response) throws JSONException {
        return new Gson().fromJson(response.toString(), Message.class);
    }

    @Override
    protected HttpOperations getHTTPMethod() {
        return HttpOperations.POST;
    }

    @Override
    protected String getPath() {
        return ServiceCatalog.USERS + "/" + AppSettings.getUser().getId() + ServiceCatalog.ALERT;
    }

    @Override
    protected JSONObject getRequestData() {

        JSONObject data = new JSONObject();
        try{
            data.put("email", mArgs.getString("email"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onInvalidToken() {

    }
}
