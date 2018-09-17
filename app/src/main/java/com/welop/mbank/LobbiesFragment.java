package com.welop.mbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.welop.svlit.mbank.R;

public class LobbiesFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    FloatingActionButton createLobby;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lobbies, container, false);

        mRecyclerView = v.findViewById(R.id.lobbies_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CardLobbyRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        createLobby = v.findViewById(R.id.lobbies_create_lobby_button);
        createLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createLobbyIntent = new Intent(view.getContext(), CreateLobbyActivity.class);
                startActivity(createLobbyIntent);
            }
        });

        return v;
    }
}
