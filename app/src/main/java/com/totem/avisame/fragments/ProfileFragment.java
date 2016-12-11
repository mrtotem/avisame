package com.totem.avisame.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.totem.avisame.R;
import com.totem.avisame.activities.MainActivity;
import com.totem.avisame.application.AppSettings;
import com.totem.avisame.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ProfileActions mListener;
    private TextView mEmail;
    private EditText mFirstname, mLastname, mFriends;
    private Button mSave;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ProfileActions) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mEmail = (TextView) v.findViewById(R.id.email);
        mFirstname = (EditText) v.findViewById(R.id.first_name);
        mLastname = (EditText) v.findViewById(R.id.last_name);
        mFriends = (EditText) v.findViewById(R.id.friends_input);
        mSave = (Button) v.findViewById(R.id.save_button);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFields();
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).disableSwipeToRefresh();
    }

    private void setListeners() {

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();

                if (mFirstname.getText() != null && !String.valueOf(mFirstname.getText()).isEmpty()) {

                    args.putString("firstName", String.valueOf(mFirstname.getText()));
                }
                if (mLastname.getText() != null && !String.valueOf(mLastname.getText()).isEmpty()) {

                    args.putString("lastName", String.valueOf(mLastname.getText()));
                }
                if (mFriends.getText() != null && !String.valueOf(mFriends.getText()).isEmpty()) {
//                    if (AppSettings.getUser().getFriends() != null) {
//                        if (!AppSettings.getUser().getFriends().contains(String.valueOf(mFriends.getText()))) {
//
//                            List<String> friends = AppSettings.getUser().getFriends();
//                            friends.add(String.valueOf(mFriends.getText()));
//                            AppSettings.getUser().setFriends(friends);
//                        }
//
//                    } else {
//
//                        List<String> friends = new ArrayList<>();
//                        friends.add(String.valueOf(mFriends.getText()));
//                        User temp = AppSettings.getUser();
//                        temp.setFriends(friends);
//                        AppSettings.setUserData(temp);
//                    }
//                    }
                    List<String> friends = new ArrayList<>();
                    friends.add(String.valueOf(mFriends.getText()));
                    User temp = AppSettings.getUser();
                    temp.setFriends(friends);
                    AppSettings.setUserData(temp);
                }
                mListener.getUserProfile(args);
            }
        });
    }

    private void setFields() {

        if (AppSettings.getUser().getEmail() != null && !AppSettings.getUser().getEmail().isEmpty()) {

            mEmail.setText(AppSettings.getUser().getEmail());
        }
        if (AppSettings.getUser().getFirstName() != null && !AppSettings.getUser().getFirstName().isEmpty()) {

            mFirstname.setText(AppSettings.getUser().getFirstName());
        }
        if (AppSettings.getUser().getLastName() != null && !AppSettings.getUser().getLastName().isEmpty()) {

            mLastname.setText(AppSettings.getUser().getLastName());
        }
        if (AppSettings.getUser().getFriends() != null) {

            mFriends.setText(AppSettings.getUser().getFriends().get(0));
        }
    }

    public interface ProfileActions {

        void getUserProfile(Bundle bundle);
    }
}
