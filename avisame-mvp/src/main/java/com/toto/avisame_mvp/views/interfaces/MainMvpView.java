package com.toto.avisame_mvp.views.interfaces;

import com.toto.avisame_mvp.models.AlertResponse;
import com.toto.avisame_mvp.models.ArrivalsResponse;
import com.toto.avisame_mvp.models.DangerResponse;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showArrivalsMessages(List<ArrivalsResponse> arrivals);

    void showAlertsMessages(List<AlertResponse> alerts);

    void showDangersMessages(List<DangerResponse> dangers);

    void showMessage(int stringId);

    void showProgressIndicator();
}
