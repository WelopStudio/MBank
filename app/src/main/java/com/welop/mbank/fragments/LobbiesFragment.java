package com.welop.mbank.fragments;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private boolean mIsFabOpened = false;
    private View mFabBackground, mLoadingBackground;
    private CoordinatorLayout mCoordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lobbies, container, false);
        initializeViews(v);
        initializeListeners(v);
        loading(true);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadData();
    }

    private void loading(boolean loading) {
        mFab.setEnabled(!loading);
        mFabCreate.setEnabled(!loading);
        mFabJoin.setEnabled(!loading);
        mLoadingBackground.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }

    private void initializeListeners(View v) {
        mFabBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFab();
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsFabOpened) {
                    closeFab();
                } else {
                    openFab();
                }
            }
        });
        mFabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFab();
                Intent intent = new Intent(getActivity(), CreateLobbyActivity.class);
                startActivity(intent);
            }
        });
        mFabJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFab();
                Intent intent = new Intent(getActivity(), JoinLobbyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeViews(View v) {
        mFabBackground = v.findViewById(R.id.fab_background);
        mLoadingBackground = v.findViewById(R.id.lobbies_loading_background);
        mFab = v.findViewById(R.id.lobbies_fab);
        mFabCreate = v.findViewById(R.id.lobbies_create_lobby_fab);
        mFabJoin = v.findViewById(R.id.lobbies_join_lobby_fab);
        mRecyclerView = v.findViewById(R.id.lobbies_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardLobbyRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mCoordinatorLayout = v.findViewById(R.id.lobbies_coordinator_layout);
    }

    private void downloadData() {
        loading(true);
        CollectionReference ref = FirebaseFirestore.getInstance().collection("wallets");
        ref.whereEqualTo("owner_id", FirebaseAuth.getInstance().getUid())
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
                                        MBank.getUser().getName(),
                                        d.getString("owner_id"),
                                        d.getString("lobby_id"),
                                        d.getString("lobby_name"),
                                        Integer.parseInt(d.getString("balance"))
                                );
                                w.setCreatedAt(d.getTimestamp("created_at"));
                                MBank.getUserWallets().add(w);
                            }
                            updateCards();
                        } else {
                            Snackbar
                                    .make(mCoordinatorLayout, "Error while downloading data.", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

    }

    private void updateCards() {
        MBank.getUserWallets().sort(Wallet.DateComparator);
        mAdapter.notifyDataSetChanged();
        loading(false);
    }

    private void openFab(){
        mIsFabOpened = true;
        mFabBackground.setVisibility(View.VISIBLE);
        mFabBackground.setAlpha(0);
        mFab.setEnabled(true);
        mFabCreate.setEnabled(true);
        mFabJoin.setEnabled(true);
        mFabBackground.animate().alpha(1);
        mFab.animate().rotationBy(-45).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mIsFabOpened) {
                    mFabBackground.setVisibility(View.GONE);
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
        mFabJoin.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        mFabCreate.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFab(){
        mIsFabOpened = false;
        mFabBackground.animate().alpha(0);
        mFab.animate().rotationBy(45);
        mFabJoin.animate().translationY(0);
        mFabCreate.animate().translationY(0);
    }

    @Override
    public boolean onBackPressed() {
        closeFab();
        return true;
    }
}
