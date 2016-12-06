package com.totem.avisame.fragments.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.totem.avisame.R;

public class SendAlertDialogFragment extends BaseDialogFragment {

    private Context context;
    private View mDismiss;

    public SendAlertDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public SendAlertDialogFragment(Context ctx) {
        this.context = ctx;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_dialog, null);

        mDismiss = view.findViewById(R.id.dismiss_screen);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
    }

    private void setListeners(){

        mDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
