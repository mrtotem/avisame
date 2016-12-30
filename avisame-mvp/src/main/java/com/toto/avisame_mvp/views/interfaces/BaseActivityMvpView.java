package com.toto.avisame_mvp.views.interfaces;

public interface BaseActivityMvpView extends MvpView {

    void showMessage(int stringId);

    void showProgressIndicator();

    void hideProgressIndicator();
}
