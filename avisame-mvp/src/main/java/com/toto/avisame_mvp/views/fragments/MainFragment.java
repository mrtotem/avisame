package com.toto.avisame_mvp.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.toto.avisame_mvp.R;
import com.toto.avisame_mvp.application.AppSettings;
import com.toto.avisame_mvp.views.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private Button mArrived, mAlert, mDanger;
    private MainFragmentActions mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            mListener = (MainFragmentActions) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mArrived = (Button) v.findViewById(R.id.arrived_button);
        mAlert = (Button) v.findViewById(R.id.alert_button);
        mDanger = (Button) v.findViewById(R.id.danger_button);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners(view);
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((MainActivity) getActivity()).disableSwipeToRefresh();
    }

    private void setListeners(final View v) {

        mArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSettings.getUser().getFriends() != null && !AppSettings.getUser().getFriends().get(0).isEmpty()) {
                    mListener.onArrivedListener();
                } else {
                    Snackbar.make(v, "Primero agregá un amigo en la sección perfil :)", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSettings.getUser().getFriends() != null && !AppSettings.getUser().getFriends().get(0).isEmpty()) {
                    mListener.onAlertListener();
                } else {
                    Snackbar.make(v, "Primero agregá un amigo en la sección perfil :)", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mDanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSettings.getUser().getFriends() != null && !AppSettings.getUser().getFriends().get(0).isEmpty()) {
                    mListener.onDangerListener();
                } else {
                    Snackbar.make(v, "Primero agregá un amigo en la sección perfil :)", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface MainFragmentActions {

        void onArrivedListener();

        void onAlertListener();

        void onDangerListener();
    }
}