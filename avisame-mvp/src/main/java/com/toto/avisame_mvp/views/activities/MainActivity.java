package com.toto.avisame_mvp.views.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.toto.avisame_mvp.R;
import com.toto.avisame_mvp.models.AlertResponse;
import com.toto.avisame_mvp.models.ArrivalsResponse;
import com.toto.avisame_mvp.models.DangerResponse;
import com.toto.avisame_mvp.views.fragments.MainFragment;
import com.toto.avisame_mvp.views.fragments.MessagesFragment;
import com.toto.avisame_mvp.views.fragments.ProfileFragment;
import com.toto.avisame_mvp.views.fragments.dialogs.SendDangerDialogFragment;
import com.toto.avisame_mvp.views.interfaces.MainMvpView;

import java.util.List;

public class MainActivity extends BaseActivity
        implements
        MainMvpView,
        MainFragment.MainFragmentActions,
        MessagesFragment.MessagesActions,
        SendDangerDialogFragment.SendDangerActions,
        ProfileFragment.ProfileActions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showArrivalsMessages(List<ArrivalsResponse> arrivals) {

    }

    @Override
    public void showAlertsMessages(List<AlertResponse> alerts) {

    }

    @Override
    public void showDangersMessages(List<DangerResponse> dangers) {

    }

    @Override
    public void showMessage(int stringId) {

    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onArrivedListener() {

    }

    @Override
    public void onAlertListener() {

    }

    @Override
    public void onDangerListener() {

    }

    @Override
    public void onMessageDangerSended(Bundle bundle) {

    }

    @Override
    public void getArrivalsMessages() {

    }

    @Override
    public void getUserProfile(Bundle bundle) {

    }

    @Override
    public void onUserLogOut() {

    }
}
