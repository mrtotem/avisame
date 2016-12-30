package com.toto.avisame_mvp.presenters;

import android.os.Bundle;
import android.util.Log;

import com.toto.avisame_mvp.R;
import com.toto.avisame_mvp.application.AvisameApplication;
import com.toto.avisame_mvp.models.User;
import com.toto.avisame_mvp.models.services.UserService;
import com.toto.avisame_mvp.views.interfaces.OnBoardingMvpView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OnBoardingPresenter implements Presenter<OnBoardingMvpView> {

    private static String TAG = "OnBoardingPresenter";

    private OnBoardingMvpView onBoardingMvpView;
    private Subscription subscription;
    private User user;

    @Override
    public void attachView(OnBoardingMvpView view) {
        this.onBoardingMvpView = view;
    }

    @Override
    public void detachView() {
        this.onBoardingMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void onUserLogin(Bundle bundle) {

        onBoardingMvpView.showProgressIndicator();

        if (subscription != null) subscription.unsubscribe();

        AvisameApplication application = AvisameApplication.get(onBoardingMvpView.getContext());
        final UserService userService = application.getUserService();
        subscription = userService.onUserLogin(new UserService.BoardingRequest(bundle.getString("email"), bundle.getString("password")))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                        Log.i(TAG, "Repos loaded " + user);
                        if (user != null) {
                            onBoardingMvpView.onSignInRequest(user);
                        } else {
                            onBoardingMvpView.showMessage(R.string.no_user_founded);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(User user) {
                        OnBoardingPresenter.this.user = user;
                    }
                });
    }

    public void onUsersSignUp(Bundle bundle) {

        onBoardingMvpView.showProgressIndicator();

        if (subscription != null) subscription.unsubscribe();

        AvisameApplication application = AvisameApplication.get(onBoardingMvpView.getContext());
        final UserService userService = application.getUserService();
        subscription = userService.onUserRegister(new UserService.BoardingRequest(bundle.getString("email"), bundle.getString("password")))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                        Log.i(TAG, "Repos loaded " + user);
                        if (user != null) {
                            onBoardingMvpView.onSignUpRequest(user);
                        } else {
                            onBoardingMvpView.showMessage(R.string.no_user_founded);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(User user) {
                        OnBoardingPresenter.this.user = user;
                    }
                });
    }
}
