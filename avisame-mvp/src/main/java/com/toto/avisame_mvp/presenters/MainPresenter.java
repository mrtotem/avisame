package com.toto.avisame_mvp.presenters;

import com.toto.avisame_mvp.models.User;
import com.toto.avisame_mvp.views.interfaces.MainMvpView;

import rx.Subscription;

public class MainPresenter implements Presenter<MainMvpView> {

    private MainMvpView mainMvpView;
    private Subscription subscription;
    private User user;

    @Override
    public void attachView(MainMvpView view) {
        this.mainMvpView = view;
    }

    @Override
    public void detachView() {
        this.mainMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }
}
