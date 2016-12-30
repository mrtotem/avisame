package com.toto.avisame_mvp.application;

import android.app.Application;
import android.content.Context;

import com.toto.avisame_mvp.models.services.MessagesService;
import com.toto.avisame_mvp.models.services.UserService;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class AvisameApplication extends Application {

    private UserService userService;
    private MessagesService messagesService;
    private Scheduler defaultSubscribeScheduler;

    @Override
    public void onCreate() {
        super.onCreate();

        AppSettings.init(this);
    }

    public static AvisameApplication get(Context context) {
        return (AvisameApplication) context.getApplicationContext();
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = UserService.UserFactory.create();
        }
        return userService;
    }

    public MessagesService getMessagesService() {
        if (messagesService == null) {
            messagesService = MessagesService.MessagesFactory.create();
        }
        return messagesService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
