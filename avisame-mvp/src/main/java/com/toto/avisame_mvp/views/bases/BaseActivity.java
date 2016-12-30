package com.toto.avisame_mvp.views.bases;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.toto.avisame_mvp.R;

public class BaseActivity extends AppCompatActivity {

    protected static final String ARG_ACTIVITY_MODE = "arg_activity_mode";
    protected static final int MODE_PARALAX = 3;

    public interface Animations {
        int FadeIn_FadeOut = 1;
        int SlideTopToBottom = 2;
        int SlideRightToLeft = 3;
        int SlideBottomToTop = 4;
    }

    protected void setBackground() {

        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
    }

    public void replaceFragment(Fragment fragment, int container) {
        replaceFragment(fragment, null, container, false, 0);
    }

    public void replaceFragment(Fragment fragment, String tag, int container, boolean addToBackStack) {
        replaceFragment(fragment, tag, container, addToBackStack, 0);
    }

    public void replaceFragment(Fragment fragment, String tag, int container, boolean addToBackstack, int animType) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (animType) {

            case Animations.FadeIn_FadeOut:

                ft.setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                break;

            case Animations.SlideTopToBottom:

                ft.setCustomAnimations(R.anim.slide_in_bottom,
                        R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom);
                break;

            case Animations.SlideRightToLeft:

                ft.setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case Animations.SlideBottomToTop:

                ft.setCustomAnimations(R.anim.slide_out_top,
                        R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_top);
                break;

            default:

                break;
        }

        ft.replace(container, fragment);

        if (addToBackstack) {
            ft.addToBackStack(tag);
        }

        ft.commit();
    }

//    public void hideKeyboard(Context context) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = ((BaseActivity) context).getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(context);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }
}