package com.toto.avisame_mvp.enums;

import com.toto.avisame_mvp.R;

/**
 * Created by Mr.Jero on 29/09/16.
 */
public enum MainTabs {

    HOME,
    MESSAGES,
    PROFILE;

    public String getTabName() {

        switch (ordinal()) {

            case 1:
                return "Mensajes";
            case 2:
                return "Perfil";
            default:
                return "Avisar!";
        }
    }

    public Integer getDrawable() {

        switch (this) {

            case HOME:
                return R.drawable.phone;

            case MESSAGES:
                return R.drawable.email;

            case PROFILE:
                return R.drawable.user_gray;

            default:
                return 0;
        }
    }
}
