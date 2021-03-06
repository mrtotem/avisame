package com.totem.avisame.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomTabLayout extends TabLayout {

    private Typeface mTypeface;

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addTab(Tab tab) {

        super.addTab(tab);
        setUpTabFont(tab);
    }

    @Override
    public void addTab(Tab tab, boolean setSelected) {

        super.addTab(tab, setSelected);
        setUpTabFont(tab);
    }

    @Override
    public void addTab(Tab tab, int position) {
        super.addTab(tab, position);
        setUpTabFont(tab);
    }

    @Override
    public void addTab(Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        setUpTabFont(tab);
    }

    public void setUpTabFont(Tab tab) {

//        if (mTypeface == null)
//            mTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hanken-Book.ttf");

        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
            }
        }
    }
}
