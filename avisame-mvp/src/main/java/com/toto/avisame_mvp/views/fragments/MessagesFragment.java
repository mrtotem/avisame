package com.toto.avisame_mvp.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toto.avisame_mvp.R;
import com.toto.avisame_mvp.models.Message;
import com.toto.avisame_mvp.views.activities.MainActivity;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MessagesActions mListener;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance() {

        Bundle args = new Bundle();

        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
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

        mListener.getArrivalsMessages();
    }

    private void setRecyclerView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    public void setAdapter(List<Message> messages) {

        Collections.sort(messages);
//        mRecyclerView.setAdapter(new MessagesAdapter(getActivity(), messages));
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((MainActivity) getActivity()).enableSwipeToRefresh();
    }

    public interface MessagesActions {

        void getArrivalsMessages();
    }
}