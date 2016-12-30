package com.toto.avisame_mvp.views.interfaces;

import com.toto.avisame_mvp.models.User;

public interface OnBoardingMvpView extends BaseActivityMvpView {

    void onSignInRequest(User loggedUser);

    void onSignUpRequest(User registeredUser);
}
