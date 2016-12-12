package com.totem.avisame.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.totem.avisame.R;
import com.totem.avisame.activities.MainActivity;
import com.totem.avisame.adapters.MessagesAdapter;
import com.totem.avisame.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Message> mAlerts;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance(ArrayList<Message> alerts) {

        Bundle args = new Bundle();

        args.putSerializable("alerts", alerts);

        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAlerts = (List<Message>) getArguments().getSerializable("alerts");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_messages, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.messages);
        setRecyclerView();

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter(mAlerts);
    }

    private void setRecyclerView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    private void setAdapter(List<Message> messages) {

        mRecyclerView.setAdapter(new MessagesAdapter(getActivity(), messages));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).enableSwipeToRefresh();
    }
}
