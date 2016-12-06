package com.totem.avisame.application;

import android.app.Application;

/**
 * Created by Octavio on 05/12/2016.
 */
public class AvisameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppSettings.init(this);
    }
}
