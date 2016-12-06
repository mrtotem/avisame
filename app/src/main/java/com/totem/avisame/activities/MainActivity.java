package com.totem.avisame.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.totem.avisame.R;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.enums.LoaderIDs;
import com.totem.avisame.enums.MainTabs;
import com.totem.avisame.fragments.MainFragment;
import com.totem.avisame.fragments.MessagesFragment;
import com.totem.avisame.fragments.ProfileFragment;
import com.totem.avisame.fragments.dialogs.SendAlertDialogFragment;
import com.totem.avisame.fragments.dialogs.SendDangerDialogFragment;
import com.totem.avisame.interfaces.LoadingViewController;
import com.totem.avisame.models.Message;
import com.totem.avisame.models.User;
import com.totem.avisame.network.base.LoaderResponse;
import com.totem.avisame.network.loaders.ArrivedMessageLoader;
import com.totem.avisame.network.loaders.SignInLoader;
import com.totem.avisame.network.loaders.UpdateUserLoader;
import com.totem.avisame.widgets.CustomTabLayout;

public class MainActivity extends AppCompatActivity
        implements
        MainFragment.MainFragmentActions,
        LoadingViewController,
        ProfileFragment.ProfileActions {

    private CustomTabLayout mTabLayout;
    private MainTabs mMainTabSelected = MainTabs.HOME;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MainTabs[] mMainTabsList = {
            MainTabs.HOME,
            MainTabs.MESSAGES,
            MainTabs.PROFILE
    };

    private LoaderManager.LoaderCallbacks<LoaderResponse<Message>> mArrivedCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<Message>>() {
        @Override
        public Loader<LoaderResponse<Message>> onCreateLoader(int id, Bundle args) {
            return new ArrivedMessageLoader(MainActivity.this, null);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<Message>> loader, LoaderResponse<Message> data) {

            if (data.getError() != null) {

            } else {

                Snackbar.make(findViewById(android.R.id.content), "Buenísimo :)", Snackbar.LENGTH_LONG).show();
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.POST_ARRIVED.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<Message>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<User>> mUpdateUserCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<User>>() {
        @Override
        public Loader<LoaderResponse<User>> onCreateLoader(int id, Bundle args) {
            return new UpdateUserLoader(MainActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<User>> loader, LoaderResponse<User> data) {

            if (data.getError() != null) {

            } else {

                AppSettings.setUserData(data.getResponse());
                AppSettings.setPushTokenValue(AppSettings.getUser().getPushToken());
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.PUT_UPDATE_USER.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<User>> loader) {

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (CustomTabLayout) findViewById(R.id.bottom_tabbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);

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
    public void showLoadingView() {
        findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        findViewById(R.id.loading_view).setVisibility(View.GONE);
    }

    @Override
    public void onArrivedListener() {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.POST_ARRIVED.getId(), null, mArrivedCallback);
    }

    @Override
    public void onAlertListener() {
        new SendAlertDialogFragment(MainActivity.this).show(getSupportFragmentManager(), "SEND-ALERT-DIALOG");
    }

    @Override
    public void onDangerListener() {
        new SendDangerDialogFragment(MainActivity.this).show(getSupportFragmentManager(), "SEND-DANGER-DIALOG");
    }

    @Override
    public void getUserProfile() {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.PUT_UPDATE_USER.getId(), null, mUpdateUserCallback);
    }
}
