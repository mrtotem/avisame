package com.totem.avisame.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.totem.avisame.R;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.enums.LoaderIDs;
import com.totem.avisame.fragments.SignInFragment;
import com.totem.avisame.fragments.SignUpFragment;
import com.totem.avisame.interfaces.LoadingViewController;
import com.totem.avisame.models.User;
import com.totem.avisame.network.base.LoaderResponse;
import com.totem.avisame.network.loaders.SignInLoader;
import com.totem.avisame.network.loaders.SignUpLoader;
import com.totem.avisame.utils.AnimUtils;

public class RegisterActivity extends AppCompatActivity
        implements
        LoadingViewController,
        SignInFragment.SignInActions,
        SignUpFragment.SignUpActions {

    private LoaderManager.LoaderCallbacks<LoaderResponse<User>> mSignUpCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<User>>() {
        @Override
        public Loader<LoaderResponse<User>> onCreateLoader(int id, Bundle args) {
            return new SignUpLoader(RegisterActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<User>> loader, LoaderResponse<User> data) {

            if (data.getError() != null) {

            } else {

                AppSettings.setUserData(data.getResponse());
                Snackbar.make(findViewById(android.R.id.content), "Bienvenid@ :)", Snackbar.LENGTH_LONG).show();

                goHome();
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.POST_REGISTER.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<User>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<User>> mSignInCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<User>>() {
        @Override
        public Loader<LoaderResponse<User>> onCreateLoader(int id, Bundle args) {
            return new SignInLoader(RegisterActivity.this, null);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<User>> loader, LoaderResponse<User> data) {

            if (data.getError() != null) {

            } else {

                AppSettings.setUserData(data.getResponse());
                Snackbar.make(findViewById(android.R.id.content), "Bienvenid@ :)", Snackbar.LENGTH_LONG).show();

                goHome();
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.POST_LOGIN.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<User>> loader) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (AppSettings.getTokenValue() != null && !AppSettings.getTokenValue().isEmpty()) {

            goHome();
        } else {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SignInFragment.newInstance())
                    .addToBackStack("login-frag")
                    .commit();
        }
    }

    private void goHome() {

        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        AnimUtils.rightInLeftOut(RegisterActivity.this);
        finish();
    }

    @Override
    public void showLoadingView() {
        findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        findViewById(R.id.loading_view).setVisibility(View.GONE);
    }

    @Override
    public void onSignInRequested(Bundle bundle) {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.POST_LOGIN.getId(), bundle, mSignInCallback);
    }

    @Override
    public void onRegisterRequested() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SignUpFragment.newInstance())
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .commit();
    }

    @Override
    public void onSignUpRequested(Bundle bundle) {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.POST_REGISTER.getId(), bundle, mSignUpCallback);
    }
}
