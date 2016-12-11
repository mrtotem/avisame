package com.totem.avisame.network.loaders;

import android.content.Context;
import android.os.Bundle;

import com.totem.avisame.application.AppSettings;
import com.totem.avisame.models.User;
import com.totem.avisame.network.ServiceCatalog;
import com.totem.avisame.network.base.HttpOperations;
import com.totem.avisame.network.base.JSONWebServiceLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpLoader extends JSONWebServiceLoader<User> {

    private Bundle mArgs;
    private Context mContext;

    public SignUpLoader(Context ctx, Bundle args) {
        super(ctx);

        this.mArgs = args;
        this.mContext = ctx;
    }

    @Override
    protected User processResponse(JSONObject response) throws JSONException {

        AppSettings.setTokenValue(response.getString("token"));
        return new User(response.getString("_id"), response.getString("email"));
    }

    @Override
    protected HttpOperations getHTTPMethod() {
        return HttpOperations.POST;
    }

    @Override
    protected String getPath() {
        return ServiceCatalog.USERS;
    }

    @Override
    protected JSONObject getRequestData() {

        JSONObject data = new JSONObject();
        try {
            if (mArgs.getString("email") != null)
                data.put("email", mArgs.getString("email"));
            if (mArgs.getString("password") != null)
                data.put("password", mArgs.getString("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onInvalidToken() {

    }
}
