package com.toto.avisame_mvp.views.activities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.toto.avisame_mvp.R;
import com.toto.avisame_mvp.application.AppSettings;
import com.toto.avisame_mvp.enums.MainTabs;
import com.toto.avisame_mvp.models.AlertResponse;
import com.toto.avisame_mvp.models.ArrivalsResponse;
import com.toto.avisame_mvp.models.DangerResponse;
import com.toto.avisame_mvp.presenters.MainPresenter;
import com.toto.avisame_mvp.views.fragments.MainFragment;
import com.toto.avisame_mvp.views.fragments.MessagesFragment;
import com.toto.avisame_mvp.views.fragments.ProfileFragment;
import com.toto.avisame_mvp.views.fragments.dialogs.SendAlertDialogFragment;
import com.toto.avisame_mvp.views.fragments.dialogs.SendDangerDialogFragment;
import com.toto.avisame_mvp.views.interfaces.MainMvpView;
import com.toto.avisame_mvp.widgets.CustomTabLayout;

import java.util.List;

public class MainActivity extends BaseActivity
        implements
        MainMvpView,
        MainFragment.MainFragmentActions,
        MessagesFragment.MessagesActions,
        SendDangerDialogFragment.SendDangerActions,
        ProfileFragment.ProfileActions,
        SendAlertDialogFragment.SendAlertActions {

    private MainPresenter presenter;

    private CustomTabLayout mTabLayout;
    private MainTabs mMainTabSelected = MainTabs.HOME;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MainTabs[] mMainTabsList = {
            MainTabs.HOME,
            MainTabs.MESSAGES,
            MainTabs.PROFILE
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter();
        presenter.attachView(this);

        mTabLayout = (CustomTabLayout) findViewById(R.id.up_tabbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                if (haveFriends()) {
//                    mSwipeRefreshLayout.setRefreshing(true);
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("email", AppSettings.getUser().getFriends().get(0));
//
//                    getSupportLoaderManager().restartLoader(LoaderIDs.GET_ARRIVALS.getId(), bundle, mGetArrivalsCallback);
//                }
            }
        });

        setUpBottomTabBar();
    }

    private void setUpBottomTabBar() {

        if (mTabLayout != null) {

            for (MainTabs mainTab : mMainTabsList) {
                TabLayout.Tab tab = mTabLayout.newTab();
//                tab.setIcon(getResources().getDrawable(mainTab.getDrawable()));
                tab.setText(mainTab.getTabName());
                mTabLayout.addTab(tab);
            }

            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mMainTabSelected = MainTabs.values()[tab.getPosition()];
                    mTabLayout.setUpTabFont(tab);
                    openSection(mMainTabsList[tab.getPosition()]);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    mTabLayout.setUpTabFont(tab);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    mTabLayout.setUpTabFont(tab);
                    openSection(mMainTabsList[tab.getPosition()]);
                }
            });

            TabLayout.Tab tab = mTabLayout.getTabAt(mMainTabSelected.ordinal());
            if (tab != null)
                tab.select();
        }
    }

    private void openSection(MainTabs tab) {

        // Clean the backstack if different than 0, clean the backstack
        if (getSupportFragmentManager().getBackStackEntryCount() != 0)
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        switch (tab) {
            case HOME: {
                transaction.replace(R.id.container, MainFragment.newInstance());
                break;
            }
            case MESSAGES: {
                transaction.replace(R.id.container, MessagesFragment.newInstance());
                break;
            }
            case PROFILE: {
                transaction.replace(R.id.container, ProfileFragment.newInstance());
                break;
            }
        }
        transaction.commit();
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
        findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndicator() {
        findViewById(R.id.loading_view).setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
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

    @Override
    public void onMessageAlertSended(Bundle bundle) {

    }
}
