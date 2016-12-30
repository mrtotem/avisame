package com.toto.avisame_mvp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.toto.avisame_mvp.R;
import com.toto.avisame_mvp.application.AppSettings;
import com.toto.avisame_mvp.models.User;
import com.toto.avisame_mvp.presenters.OnBoardingPresenter;
import com.toto.avisame_mvp.utils.AnimUtils;
import com.toto.avisame_mvp.views.fragments.SignInFragment;
import com.toto.avisame_mvp.views.fragments.SignUpFragment;
import com.toto.avisame_mvp.views.interfaces.OnBoardingMvpView;

public class OnBoardingActivity extends BaseActivity
        implements
        OnBoardingMvpView,
        SignUpFragment.SignUpActions {

    private OnBoardingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (AppSettings.getTokenValue() != null && !AppSettings.getTokenValue().isEmpty()) {

            goHome();
        } else {

            replaceFragment(SignInFragment.newInstance(), "login-frag", R.id.container, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        int cont = 0, i;

        for (i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == 0) {

                cont++;
            }
        }
        if (cont == 2) {
            goHome();
        }
    }

    private void goHome() {

        startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
        AnimUtils.rightInLeftOut(OnBoardingActivity.this);
        finish();
    }

    @Override
    public void onSignUpRequested(Bundle bundle) {

    }

    @Override
    public void onSignInRequest(User loggedUser) {

    }

    @Override
    public void onSignUpRequest(User registeredUser) {

    }

    @Override
    public void showMessage(int stringId) {

    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}