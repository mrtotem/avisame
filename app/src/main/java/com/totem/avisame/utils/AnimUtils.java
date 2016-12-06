package com.totem.avisame.utils;

import android.app.Activity;

import com.totem.avisame.R;

public class AnimUtils {

    public static void rightInLeftOut(Activity activity){

        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void leftInRightOut(Activity activity){

        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void bottonInTopOut(Activity activity){

        activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_right);
    }

    public static void rightInRightOut(Activity activity){

        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
