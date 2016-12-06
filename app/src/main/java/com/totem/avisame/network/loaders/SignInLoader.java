package com.totem.avisame.network.loaders;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.models.Message;
import com.totem.avisame.models.User;
import com.totem.avisame.network.ServiceCatalog;
import com.totem.avisame.network.base.HttpOperations;
import com.totem.avisame.network.base.JSONWebServiceLoader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Octavio on 03/12/2016.
 */
public class SignInLoader extends JSONWebServiceLoader<User> {

    private Bundle mArgs;
    private Context mContext;

    public SignInLoader(Context ctx, Bundle args) {
        super(ctx);

        this.mContext = ctx;
        this.mArgs = args;
    }

    @Override
    protected User processResponse(JSONObject response) throws JSONException {

        AppSettings.setTokenValue(response.getString("token"));
        return new Gson().fromJson(response.toString(), User.class);
    }

    @Override
    protected HttpOperations getHTTPMethod() {
        return HttpOperations.POST;
    }

    @Override
    protected String getPath() {
        return ServiceCatalog.USERS + ServiceCatalog.LOGIN;
    }

    @Override
    protected JSONObject getRequestData() {

        JSONObject data = new JSONObject();
        try{
            data.put("email", "jero@jero.com");
            data.put("password", "12345678");
        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onInvalidToken() {

    }
}
