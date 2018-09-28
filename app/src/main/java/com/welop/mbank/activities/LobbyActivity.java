package com.welop.mbank.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.welop.mbank.MBank;
import com.welop.mbank.adapters.CardPlayerLobbyRecyclerAdapter;
import com.welop.mbank.model.Wallet;
import com.welop.svlit.mbank.R;

public class LobbyActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private Bundle mExtras;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private TextView mAccountName;
    private TextView mWalletName;
    private TextView mWalletBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        mExtras = getIntent().getExtras();
        initializeViews();
        initializeListeners();
        showInviteCodeIfFirstStartup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloadData();
    }

    private void showInviteCodeIfFirstStartup() {
        if (mExtras != null)
            if (mExtras.getBoolean("just_created")) {
                Snackbar
                        .make(mCoordinatorLayout, "Use " + mExtras.getString("invite_code") + " as invite code.", Snackbar.LENGTH_LONG)
                        .setAction("COPY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData data = ClipData.newPlainText("Invite code", "ABCDE");
                                manager.setPrimaryClip(data);
                                Snackbar
                                        .make(mCoordinatorLayout, "Invite code copied to clipboard.", BaseTransientBottomBar.LENGTH_SHORT);
                            }
                        })
                        .show();
            }
    }

    private void initializeListeners() {

    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.lobby_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lobby \"" + mExtras.getString("lobby_name") + "\"");
        mCoordinatorLayout = findViewById(R.id.lobby_coordinator_layout);
        mAccountName = findViewById(R.id.lobby_account_name);
        mWalletName = findViewById(R.id.lobby_wallet_name);
        mWalletBalance = findViewById(R.id.lobby_wallet_balance);
        mRecyclerView = findViewById(R.id.lobby_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardPlayerLobbyRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void downloadData() {
        CollectionReference ref = FirebaseFirestore.getInstance().collection("wallets");
        ref.whereEqualTo("lobby_id", mExtras.getString("lobby_id"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            MBank.getLobbyWallets().clear();
                            for (DocumentSnapshot d : snapshot) {
                                Wallet w = new Wallet(
                                        d.getString("name"),
                                        d.getString("owner_name"),
                                        d.getString("owner_id"),
                                        d.getString("lobby_id"),
                                        d.getString("lobby_name"),
                                        Integer.parseInt(d.getString("balance"))
                                );
                                if (w.getOwnerId().equals(FirebaseAuth.getInstance().getUid()))
                                    initializeMainCard(w);
                                //else
                                    MBank.getLobbyWallets().add(w);
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

    private void initializeMainCard(Wallet w) {
        mAccountName.setText(MBank.getUser().getName());
        mWalletName.setText(w.getName());
        mWalletBalance.setText(Integer.toString(w.getBalance()));
    }

    private void updateCards() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.lobby_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.lobby_menu_ready).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.lobby_menu_ready:
                if (item.isChecked()) {
                    item.setChecked(false);
                    // TODO: Этот код не работает
                    Snackbar.make(findViewById(R.id.lobby_coordinator_layout), "Checked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                else {
                    item.setChecked(true);
                    Snackbar.make(findViewById(R.id.lobby_coordinator_layout), "Unchecked", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                break;
            case R.id.lobby_menu_leave:
                Snackbar.make(findViewById(R.id.lobby_coordinator_layout), "Imagine you have left", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        return true;
    }
}
