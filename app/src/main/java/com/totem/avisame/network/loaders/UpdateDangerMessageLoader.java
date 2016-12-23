package com.totem.avisame.network.loaders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.totem.avisame.activities.RegisterActivity;
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
public class UpdateDangerMessageLoader extends JSONWebServiceLoader<Message> {

    private Bundle mArgs;
    private Context mContext;

    public UpdateDangerMessageLoader(Context ctx, Bundle args) {
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
        return HttpOperations.PUT;
    }

    @Override
    protected String getPath() {
        return ServiceCatalog.USERS + "/" + AppSettings.getUser().getId() + ServiceCatalog.DANGER + "/" + mArgs.getString("message-id");
    }

    @Override
    protected JSONObject getRequestData() {

        JSONObject data = new JSONObject();
        try{
            data.put("date", mArgs.getString("date"));
            data.put("message", mArgs.getString("message"));
            data.put("lat", mArgs.getString("lat"));
            data.put("long", mArgs.getString("long"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onInvalidToken() {

        mContext.startActivity(new Intent(mContext, RegisterActivity.class));
    }
}
