package com.welop.mbank.fragments;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.welop.mbank.MBank;
import com.welop.mbank.activities.CreateLobbyActivity;
import com.welop.mbank.activities.JoinLobbyActivity;
import com.welop.mbank.adapters.CardLobbyRecyclerAdapter;
import com.welop.mbank.interfaces.OnBackPressedListener;
import com.welop.mbank.model.Wallet;
import com.welop.svlit.mbank.R;

public class LobbiesFragment extends Fragment implements OnBackPressedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton mFab, mFabCreate, mFabJoin;
    private boolean mIsFabOpen = false;
    private View mFabBackGround;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lobbies, container, false);
        receiveAndShowLobbies(v);

        mProgressBar = v.findViewById(R.id.lobbies_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
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
                if (!mIsFabOpen) {
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

    private void receiveAndShowLobbies(final View v) {

        CollectionReference ref = FirebaseFirestore.getInstance().collection("wallets");
        ref.whereEqualTo("owner_id", FirebaseAuth.getInstance().getUid().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            MBank.getUserWallets().clear();
                            for (DocumentSnapshot d : snapshot) {
                                Wallet w = new Wallet(
                                        d.getString("name"),
                                        d.getString("owner_id"),
                                        d.getString("lobby_id"),
                                        d.getString("lobby_name"),
                                        Integer.parseInt(d.getString("balance"))
                                );
                                MBank.getUserWallets().add(w);
                            }
                            adapt(v);
                        } else {
                            // Не получилось
                        }
                    }
                });

    }

    private void adapt(View v) {
        mRecyclerView = v.findViewById(R.id.lobbies_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardLobbyRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showFABMenu(){
        mIsFabOpen = true;

        mFabBackGround.setVisibility(View.VISIBLE);
        mFab.setVisibility(View.VISIBLE);
        mFabCreate.setVisibility(View.VISIBLE);
        mFabJoin.setVisibility(View.VISIBLE);

        mFab.animate().rotationBy(-45);
        mFabJoin.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        mFabCreate.animate().translationY(-getResources().getDimension(R.dimen.standard_100));

    }

    private void closeFABMenu(){
        mIsFabOpen = false;

        mFabBackGround.setVisibility(View.GONE);
        mFab.animate().rotationBy(45);
        mFabJoin.animate().translationY(0);
        mFabCreate.animate().translationY(0).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mIsFabOpen) {
                    mFabCreate.setVisibility(View.GONE);
                    mFabJoin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {

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
