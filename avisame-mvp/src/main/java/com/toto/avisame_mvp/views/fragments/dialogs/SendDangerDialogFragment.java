package com.toto.avisame_mvp.views.fragments.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.toto.avisame_mvp.R;
import com.toto.avisame_mvp.models.Location;
import com.toto.avisame_mvp.models.Message;
import com.toto.avisame_mvp.utils.DateUtils;
import com.toto.avisame_mvp.utils.LocationUtils;
import com.toto.avisame_mvp.views.activities.MainActivity;

import java.util.Date;

public class SendDangerDialogFragment extends BaseDialogFragment {

    private Context context;
    private View mDismiss;
    private EditText mMessage;
    private Button mSendButton;
    private SendDangerActions mListener;

    private Message mDanger;

    public SendDangerDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public SendDangerDialogFragment(Context ctx) {
        this.context = ctx;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (MainActivity) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danger_dialog, null);

        mDismiss = view.findViewById(R.id.dismiss_screen);
        mSendButton = (Button) view.findViewById(R.id.send_danger_button);
        mMessage = (EditText) view.findViewById(R.id.danger_text);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
    }

    private void setListeners() {

        mDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                if (mDanger != null) {

                    if (String.valueOf(mMessage.getText()) != null && !String.valueOf(mMessage.getText()).isEmpty()) {

                        args.putString("message", String.valueOf(mMessage.getText()));
                    }

                    args.putString("date", mDanger.getDate());
                    args.putString("message-id", mDanger.getId());
                } else {

                    Date date = new Date();
                    args.putString("date", DateUtils.formatToString(date));

                }
                Location loc = LocationUtils.getUserLocation(getActivity());
                args.putString("lat", String.valueOf(loc != null ? loc.getLatitude() : 0));
                args.putString("long", String.valueOf(loc != null ? loc.getLongitude() : 0));

                mListener.onMessageDangerSended(args);
                dismiss();
            }
        });
    }

    public void setDanger(Message danger) {
        this.mDanger = danger;
    }

    public interface SendDangerActions {

        void onMessageDangerSended(Bundle bundle);
    }
}
