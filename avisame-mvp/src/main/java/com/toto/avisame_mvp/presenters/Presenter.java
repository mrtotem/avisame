package com.toto.avisame_mvp.presenters;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();
}
