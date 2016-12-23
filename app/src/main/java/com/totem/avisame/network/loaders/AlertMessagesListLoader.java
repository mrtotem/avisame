package com.totem.avisame.network.loaders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.totem.avisame.activities.RegisterActivity;
import com.totem.avisame.models.AlertResponse;
import com.totem.avisame.models.Message;
import com.totem.avisame.network.ServiceCatalog;
import com.totem.avisame.network.base.HttpOperations;
import com.totem.avisame.network.base.JSONArrayWebServiceLoader;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Octavio on 14/12/2016.
 */

public class AlertMessagesListLoader extends JSONArrayWebServiceLoader<AlertResponse> {

    private Bundle mArgs;
    private Context mContext;

    public AlertMessagesListLoader(Context ctx, Bundle bundle) {
        super(ctx);

        this.mArgs = bundle;
    }

    @Override
    protected AlertResponse processResponse(JSONArray response) {
        List<Message> alerts = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            try {
                alerts.add(new Gson().fromJson(response.get(i).toString(), Message.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return new AlertResponse(alerts);
    }

    @Override
    protected HttpOperations getHTTPMethod() {
        return HttpOperations.GET;
    }

    @Override
    protected String getPath() {
        return ServiceCatalog.ALERT + "/" + mArgs.getString("email");
    }

    @Override
    protected void onInvalidToken() {

        mContext.startActivity(new Intent(mContext, RegisterActivity.class));
    }
}
