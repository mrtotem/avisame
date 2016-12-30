package com.toto.avisame_mvp.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.toto.avisame_mvp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private EditText mEmail, mPassword;
    private Button mLoginButton, mRegisterButton;
    private SignInActions mListener;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {

        Bundle args = new Bundle();

        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SignInActions) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mEmail = (EditText) v.findViewById(R.id.email_field);
        mPassword = (EditText) v.findViewById(R.id.password_field);
        mLoginButton = (Button) v.findViewById(R.id.login_button);
        mRegisterButton = (Button) v.findViewById(R.id.register_button);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
    }

    private void setListeners() {

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((mEmail.getText() != null && !String.valueOf(mEmail.getText()).isEmpty())
                        && (mPassword.getText() != null && !String.valueOf(mPassword.getText()).isEmpty())) {
                    Bundle args = new Bundle();

                    args.putString("email", String.valueOf(mEmail.getText()));
                    args.putString("password", String.valueOf(mPassword.getText()));
                    mListener.onSignInRequested(args);
                }
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToSignUp();
            }
        });
    }

    public interface SignInActions {

        void onSignInRequested(Bundle bundle);

        void goToSignUp();
    }
}
