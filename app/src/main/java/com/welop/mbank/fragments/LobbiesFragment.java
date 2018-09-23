package com.welop.mbank.fragments;

import android.animation.Animator;
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

import com.welop.mbank.activities.CreateLobbyActivity;
import com.welop.mbank.activities.CreateWalletActivity;
import com.welop.mbank.activities.JoinLobbyActivity;
import com.welop.mbank.adapters.CardLobbyRecyclerAdapter;
import com.welop.mbank.interfaces.OnBackPressedListener;
import com.welop.svlit.mbank.R;

public class LobbiesFragment extends Fragment implements OnBackPressedListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    private FloatingActionButton mFab, mFabCreate, mFabJoin;
    boolean isFABOpen=false;
    View mFabBackGround;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lobbies, container, false);

        mRecyclerView = v.findViewById(R.id.lobbies_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CardLobbyRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mFabBackGround = v.findViewById(R.id.fab_background);
        mFab = v.findViewById(R.id.lobbies_fab);
        mFabCreate = v.findViewById(R.id.lobbies_create_lobby_fab);
        mFabJoin = v.findViewById(R.id.lobbies_join_lobby_fab);

        mFabBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        mFabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateLobbyActivity.class);
                startActivity(intent);
            }
        });

        mFabJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JoinLobbyActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void showFABMenu(){
        isFABOpen = true;

        mFabBackGround.setVisibility(View.VISIBLE);
        mFab.setVisibility(View.VISIBLE);
        mFabCreate.setVisibility(View.VISIBLE);
        mFabJoin.setVisibility(View.VISIBLE);

        mFab.animate().rotationBy(-45);
        mFabJoin.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        mFabCreate.animate().translationY(-getResources().getDimension(R.dimen.standard_100));

    }

    private void closeFABMenu(){
        isFABOpen = false;

        mFabBackGround.setVisibility(View.GONE);
        mFab.animate().rotationBy(45);
        mFabJoin.animate().translationY(0);
        mFabCreate.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isFABOpen) {
                    mFabCreate.setVisibility(View.GONE);
                    mFabJoin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

    @Override
    public boolean onBackPressed() {
        closeFABMenu();
        return true;
    }
}
