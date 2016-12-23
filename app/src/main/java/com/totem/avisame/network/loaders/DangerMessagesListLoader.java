package com.totem.avisame.network.loaders;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.totem.avisame.models.AlertResponse;
import com.totem.avisame.models.DangerResponse;
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

public class DangerMessagesListLoader extends JSONArrayWebServiceLoader<DangerResponse> {

    private Bundle mArgs;
    private Context mContext;

    public DangerMessagesListLoader(Context ctx, Bundle bundle) {
        super(ctx);

        this.mArgs = bundle;
    }

    @Override
    protected DangerResponse processResponse(JSONArray response) {
        List<Message> dangers = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            try {
                dangers.add(new Gson().fromJson(response.get(i).toString(), Message.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return new DangerResponse(dangers);
    }

    @Override
    protected HttpOperations getHTTPMethod() {
        return HttpOperations.GET;
    }

    @Override
    protected String getPath() {
        return ServiceCatalog.DANGER + "/" + mArgs.getString("email");
    }

    @Override
    protected void onInvalidToken() {

    }
}