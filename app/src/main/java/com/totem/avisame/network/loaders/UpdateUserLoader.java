package com.totem.avisame.network.loaders;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.totem.avisame.TokenManager;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.models.User;
import com.totem.avisame.network.ServiceCatalog;
import com.totem.avisame.network.base.HttpOperations;
import com.totem.avisame.network.base.JSONWebServiceLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUserLoader extends JSONWebServiceLoader<User> {

    private Bundle mArgs;
    private Context mContext;

    public UpdateUserLoader(Context ctx, Bundle args) {
        super(ctx);

        this.mArgs = args;
        this.mContext = ctx;
    }

    @Override
    protected User processResponse(JSONObject response) throws JSONException {
        return new Gson().fromJson(response.toString(), User.class);
    }

    @Override
    protected HttpOperations getHTTPMethod() {
        return HttpOperations.PUT;
    }

    @Override
    protected String getPath() {
        return ServiceCatalog.USERS + "/" + AppSettings.getUser().getId();
    }

    @Override
    protected JSONObject getRequestData() {

        JSONObject data = new JSONObject();
        try {
            if (TokenManager.getInstance().getToken() != null)
                data.put("pushToken", TokenManager.getInstance().getToken());
            if (mArgs.getString("firstName") != null)
                data.put("firstName", mArgs.getString("firstName"));
            if (mArgs.getString("lastName") != null)
                data.put("lastName", mArgs.getString("lastName"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onInvalidToken() {

    }
}
