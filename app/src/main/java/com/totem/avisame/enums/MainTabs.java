package com.totem.avisame.enums;

import com.totem.avisame.R;

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
                return "Botones";
        }
    }

//    public Integer getDrawable() {
//
//        switch (this) {
//
//            case HOME:
//                return R.drawable.store_gray;
//
//            case CHAIR:
//                return R.drawable.caldendar_gray;
//
//            case PROFILE:
//                return R.drawable.user_gray;
//
//            default:
//                return 0;
//        }
//    }
}
