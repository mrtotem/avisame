package com.totem.avisame.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import com.totem.avisame.R;
import com.totem.avisame.activities.bases.BaseActivity;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.enums.LoaderIDs;
import com.totem.avisame.fragments.SignInFragment;
import com.totem.avisame.fragments.SignUpFragment;
import com.totem.avisame.interfaces.LoadingViewController;
import com.totem.avisame.models.User;
import com.totem.avisame.network.base.LoaderResponse;
import com.totem.avisame.network.loaders.SignInLoader;
import com.totem.avisame.network.loaders.SignUpLoader;
import com.totem.avisame.network.loaders.UpdateUserLoader;
import com.totem.avisame.utils.AnimUtils;
import com.totem.avisame.utils.AppUtils;

public class RegisterActivity extends BaseActivity
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
                hideLoadingView();
            } else {

                AppSettings.setUserData(data.getResponse());
                getSupportLoaderManager().restartLoader(LoaderIDs.PUT_UPDATE_USER.getId(), null, mUpdateUserCallback);
            }

            getSupportLoaderManager().destroyLoader(LoaderIDs.POST_REGISTER.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<User>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<User>> mUpdateUserCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<User>>() {
        @Override
        public Loader<LoaderResponse<User>> onCreateLoader(int id, Bundle args) {
            return new UpdateUserLoader(RegisterActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<User>> loader, LoaderResponse<User> data) {

            if (data.getError() != null) {

                Snackbar.make(findViewById(android.R.id.content), data.getError().getMessage(), Snackbar.LENGTH_LONG).show();
            } else {

                AppSettings.setUserData(data.getResponse());
                if (AppUtils.checkPermissions(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    Snackbar.make(findViewById(android.R.id.content), "Bienvenid@ :)", Snackbar.LENGTH_LONG).show();
                    goHome();
                } else {

                    AppUtils.requestPermissions(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                }
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.PUT_UPDATE_USER.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<User>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<User>> mSignInCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<User>>() {
        @Override
        public Loader<LoaderResponse<User>> onCreateLoader(int id, Bundle args) {
            return new SignInLoader(RegisterActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<User>> loader, LoaderResponse<User> data) {

            if (data.getError() != null) {

                Snackbar.make(findViewById(android.R.id.content), data.getError().getMessage(), Snackbar.LENGTH_LONG).show();
                hideLoadingView();
            } else {

                AppSettings.setUserData(data.getResponse());
                getSupportLoaderManager().restartLoader(LoaderIDs.PUT_UPDATE_USER.getId(), null, mUpdateUserCallback);
            }
            getSupportLoaderManager().destroyLoader(LoaderIDs.POST_LOGIN.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<User>> loader) {

        }
    };

    private int pendingWidgetFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getIntent() != null && getIntent().getExtras() != null) {
            pendingWidgetFlag = getIntent().getExtras().getInt("PENDING_FLAG");
        }

        if (AppSettings.getTokenValue() != null && !AppSettings.getTokenValue().isEmpty()) {

            goHome();
        } else {

            replaceFragment(SignInFragment.newInstance(), "login-frag", R.id.container, false, Animations.SlideRightToLeft);
        }
    }

    private void goHome() {

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        if (pendingWidgetFlag != 0) {
            intent.putExtra("PENDING_FLAG", pendingWidgetFlag);
        }
        startActivity(intent);
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
    public void goToSignUp() {

        replaceFragment(SignUpFragment.newInstance(), "register-frag", R.id.container, true, Animations.SlideRightToLeft);
    }

    @Override
    public void onSignUpRequested(Bundle bundle) {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.POST_REGISTER.getId(), bundle, mSignUpCallback);
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
}
