package com.totem.avisame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.totem.avisame.R;
import com.totem.avisame.adapters.MessagesAdapter;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.enums.LoaderIDs;
import com.totem.avisame.enums.MainTabs;
import com.totem.avisame.fragments.MainFragment;
import com.totem.avisame.fragments.MessagesFragment;
import com.totem.avisame.fragments.ProfileFragment;
import com.totem.avisame.fragments.dialogs.SendAlertDialogFragment;
import com.totem.avisame.fragments.dialogs.SendDangerDialogFragment;
import com.totem.avisame.interfaces.LoadingViewController;
import com.totem.avisame.models.AlertResponse;
import com.totem.avisame.models.DangerResponse;
import com.totem.avisame.models.Message;
import com.totem.avisame.models.User;
import com.totem.avisame.network.base.LoaderResponse;
import com.totem.avisame.network.loaders.AlertMessageLoader;
import com.totem.avisame.network.loaders.ArrivedMessageLoader;
import com.totem.avisame.network.loaders.DangerMessageLoader;
import com.totem.avisame.network.loaders.GetAlertMessagesLoader;
import com.totem.avisame.network.loaders.GetDangerMessagesLoader;
import com.totem.avisame.network.loaders.SignInLoader;
import com.totem.avisame.network.loaders.UpdateAlertMessageLoader;
import com.totem.avisame.network.loaders.UpdateDangerMessageLoader;
import com.totem.avisame.network.loaders.UpdateUserLoader;
import com.totem.avisame.utils.AnimUtils;
import com.totem.avisame.widgets.CustomTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        MainFragment.MainFragmentActions,
        LoadingViewController,
        ProfileFragment.ProfileActions,
        SendAlertDialogFragment.SendAlertActions,
        MessagesAdapter.MessageActions,
        MessagesFragment.MessagesActions,
        SendDangerDialogFragment.SendDangerActions {

    private CustomTabLayout mTabLayout;
    private MainTabs mMainTabSelected = MainTabs.HOME;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MainTabs[] mMainTabsList = {
            MainTabs.HOME,
            MainTabs.MESSAGES,
            MainTabs.PROFILE
    };

    private List<Message> allMessages;

    private LoaderManager.LoaderCallbacks<LoaderResponse<Message>> mArrivedCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<Message>>() {
        @Override
        public Loader<LoaderResponse<Message>> onCreateLoader(int id, Bundle args) {
            return new ArrivedMessageLoader(MainActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<Message>> loader, LoaderResponse<Message> data) {

            if (data.getError() != null) {
                Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error :/", Snackbar.LENGTH_SHORT).show();
            } else {

                Snackbar.make(findViewById(android.R.id.content), "Buenísimo :)", Snackbar.LENGTH_SHORT).show();
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.POST_ARRIVED.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<Message>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<Message>> mAlertCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<Message>>() {
        @Override
        public Loader<LoaderResponse<Message>> onCreateLoader(int id, Bundle args) {
            return new AlertMessageLoader(MainActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<Message>> loader, LoaderResponse<Message> data) {

            if (data.getError() != null) {
                Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error :/", Snackbar.LENGTH_SHORT).show();
            } else {

                Snackbar.make(findViewById(android.R.id.content), ":/", Snackbar.LENGTH_SHORT).show();
                Fragment frag = getSupportFragmentManager().findFragmentByTag("SEND-ALERT-DIALOG");
                if (frag instanceof SendAlertDialogFragment) {
                    ((SendAlertDialogFragment) frag).setmAlert(data.getResponse());
                }
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.POST_ALERT.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<Message>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<Message>> mUpdateAlertCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<Message>>() {
        @Override
        public Loader<LoaderResponse<Message>> onCreateLoader(int id, Bundle args) {
            return new UpdateAlertMessageLoader(MainActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<Message>> loader, LoaderResponse<Message> data) {

            if (data.getError() != null) {

                Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error :/", Snackbar.LENGTH_SHORT).show();
            } else {

                Snackbar.make(findViewById(android.R.id.content), "Mensaje enviado!", Snackbar.LENGTH_SHORT).show();
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.PUT_ALERT.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<Message>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<Message>> mDangerCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<Message>>() {
        @Override
        public Loader<LoaderResponse<Message>> onCreateLoader(int id, Bundle args) {
            return new DangerMessageLoader(MainActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<Message>> loader, LoaderResponse<Message> data) {

            if (data.getError() != null) {
                Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error :/", Snackbar.LENGTH_SHORT).show();
            } else {

                Snackbar.make(findViewById(android.R.id.content), ":/ :/", Snackbar.LENGTH_SHORT).show();

                Fragment frag = getSupportFragmentManager().findFragmentByTag("SEND-DANGER-DIALOG");
                if (frag instanceof SendDangerDialogFragment) {
                    ((SendDangerDialogFragment) frag).setDanger(data.getResponse());
                }
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.POST_DANGER.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<Message>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<Message>> mUpdateDangerCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<Message>>() {
        @Override
        public Loader<LoaderResponse<Message>> onCreateLoader(int id, Bundle args) {
            return new UpdateDangerMessageLoader(MainActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<Message>> loader, LoaderResponse<Message> data) {

            if (data.getError() != null) {

                Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error :/", Snackbar.LENGTH_SHORT).show();
            } else {

                Snackbar.make(findViewById(android.R.id.content), "Mensaje enviado!", Snackbar.LENGTH_SHORT).show();
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.PUT_DANGER.getId());
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
                Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error :/", Snackbar.LENGTH_SHORT).show();
            } else {

                User temp = data.getResponse();
                temp.setFriends(AppSettings.getUser().getFriends());
                AppSettings.setUserData(temp);

                Snackbar.make(findViewById(android.R.id.content), "Perfil guardado :)", Snackbar.LENGTH_SHORT).show();
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.PUT_UPDATE_USER.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<User>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<AlertResponse>> mGetAlertsCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<AlertResponse>>() {
        @Override
        public Loader<LoaderResponse<AlertResponse>> onCreateLoader(int id, Bundle args) {
            return new GetAlertMessagesLoader(MainActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<AlertResponse>> loader, LoaderResponse<AlertResponse> data) {

            if (data.getError() != null) {
                Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error :/", Snackbar.LENGTH_SHORT).show();
            } else {

                allMessages = data.getResponse().getAlerts();
                Bundle bundle = new Bundle();
                bundle.putString("email", AppSettings.getUser().getFriends().get(0));
                getSupportLoaderManager().restartLoader(LoaderIDs.GET_DANGERS.getId(), bundle, mGetDangersCallback);
            }

            getSupportLoaderManager().destroyLoader(LoaderIDs.GET_ALERTS.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<AlertResponse>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<LoaderResponse<DangerResponse>> mGetDangersCallback = new LoaderManager.LoaderCallbacks<LoaderResponse<DangerResponse>>() {
        @Override
        public Loader<LoaderResponse<DangerResponse>> onCreateLoader(int id, Bundle args) {
            return new GetDangerMessagesLoader(MainActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResponse<DangerResponse>> loader, LoaderResponse<DangerResponse> data) {

            if (data.getError() != null) {
                Snackbar.make(findViewById(android.R.id.content), "Ocurrió un error :/", Snackbar.LENGTH_SHORT).show();
            } else {

                allMessages.addAll(data.getResponse().getDangers());
                Fragment frag = getSupportFragmentManager().findFragmentById(R.id.container);
                if (frag instanceof MessagesFragment) {

                    ((MessagesFragment) frag).setAdapter(allMessages);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            hideLoadingView();
            getSupportLoaderManager().destroyLoader(LoaderIDs.GET_DANGERS.getId());
        }

        @Override
        public void onLoaderReset(Loader<LoaderResponse<DangerResponse>> loader) {

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

                if (haveFriends()) {
                    mSwipeRefreshLayout.setRefreshing(true);

                    Bundle bundle = new Bundle();
                    bundle.putString("email", AppSettings.getUser().getFriends().get(0));

                    getSupportLoaderManager().restartLoader(LoaderIDs.GET_ALERTS.getId(), bundle, mGetAlertsCallback);
                }
            }
        });

        setUpBottomTabBar();
    }

    private void setUpBottomTabBar() {

        if (mTabLayout != null) {

            for (MainTabs mainTab : mMainTabsList) {
                TabLayout.Tab tab = mTabLayout.newTab();
                tab.setIcon(getResources().getDrawable(mainTab.getDrawable()));
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

        Bundle args = new Bundle();
        args.putString("email", AppSettings.getUser().getFriends().get(0));

        getSupportLoaderManager().restartLoader(LoaderIDs.POST_ARRIVED.getId(), args, mArrivedCallback);
    }

    @Override
    public void onAlertListener() {

        Bundle args = new Bundle();
        args.putString("email", AppSettings.getUser().getFriends().get(0));

        getSupportLoaderManager().restartLoader(LoaderIDs.POST_ALERT.getId(), args, mAlertCallback);

        new SendAlertDialogFragment(MainActivity.this).show(getSupportFragmentManager(), "SEND-ALERT-DIALOG");
    }

    @Override
    public void onDangerListener() {

        Bundle args = new Bundle();
        args.putString("email", AppSettings.getUser().getFriends().get(0));

        getSupportLoaderManager().restartLoader(LoaderIDs.POST_DANGER.getId(), args, mDangerCallback);

        new SendDangerDialogFragment(MainActivity.this).show(getSupportFragmentManager(), "SEND-DANGER-DIALOG");
    }

    @Override
    public void getUserProfile(Bundle bundle) {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.PUT_UPDATE_USER.getId(), bundle, mUpdateUserCallback);
    }

    @Override
    public void onUserLogOut() {

        AppSettings.cleanSettings();
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        AnimUtils.rightInLeftOut(MainActivity.this);
        finish();
    }

    public void disableSwipeToRefresh() {
        mSwipeRefreshLayout.setEnabled(false);
    }

    public void enableSwipeToRefresh() {
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onMessageAlertSended(Bundle bundle) {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.PUT_ALERT.getId(), bundle, mUpdateAlertCallback);
    }

    @Override
    public void onLocationShow(Intent mapIntent) {
        startActivity(mapIntent);
    }

    @Override
    public void getAlertMessages() {

        if (haveFriends()) {

            Bundle bundle = new Bundle();
            bundle.putString("email", AppSettings.getUser().getFriends().get(0));

            showLoadingView();
            getSupportLoaderManager().restartLoader(LoaderIDs.GET_ALERTS.getId(), bundle, mGetAlertsCallback);
        }
    }

    private boolean haveFriends() {

        if (AppSettings.getUser().getFriends() != null) {
            return true;
        }
        Snackbar.make(findViewById(android.R.id.content), "Primero agregá un amigo en la sección perfil :)", Snackbar.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMessageDangerSended(Bundle bundle) {

        showLoadingView();
        getSupportLoaderManager().restartLoader(LoaderIDs.PUT_DANGER.getId(), bundle, mUpdateDangerCallback);
    }
}
