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
import com.totem.avisame.interfaces.LoadingViewController;
import com.totem.avisame.models.User;
import com.totem.avisame.network.base.LoaderResponse;
import com.totem.avisame.network.loaders.SignInLoader;
import com.totem.avisame.utils.AnimUtils;

public class RegisterActivity extends AppCompatActivity
        implements
        LoadingViewController,
        SignInFragment.SignInActions{

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
                AppSettings.setTokenValue(AppSettings.getUser().getToken());
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

        if(AppSettings.getUser() != null && AppSettings.getUser().getToken() != null && !AppSettings.getUser().getToken().isEmpty()){

            goHome();
        }else{

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SignInFragment.newInstance())
                    .commit();
        }
    }

    private void goHome(){

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
    public void onSignInRequested() {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.POST_LOGIN.getId(), null, mSignInCallback);
    }
}
