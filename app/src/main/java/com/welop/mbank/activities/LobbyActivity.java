package com.welop.mbank.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.welop.mbank.MBank;
import com.welop.mbank.adapters.PlayerLobbyRecyclerAdapter;
import com.welop.mbank.adapters.TransactionRecyclerAdapter;
import com.welop.mbank.model.Lobby;
import com.welop.mbank.model.Wallet;
import com.welop.svlit.mbank.R;

import javax.annotation.Nullable;

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
    //private FloatingActionButton mHistoryButton;
    private ProgressBar mProgressBar;
    private ImageView mAdmin;

    private RecyclerView mRecyclerViewBottobSheet;
    private RecyclerView.LayoutManager mLayoutManagerBottobSheet;
    private RecyclerView.Adapter mAdapterBottomSheet;

    private BottomSheetBehavior mSheetBehavior;
    private ConstraintLayout mLayoutBottomSheet;
    private Button mBtnBottomSheet;

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
        downloadLobby();
    }

    private void showInviteCodeIfFirstStartup() {
        if (mExtras != null)
            if (mExtras.getBoolean("just_created")) {
                Snackbar
                        .make(mLayoutBottomSheet, "Use " + mExtras.getString("invite_code") + " as invite code.", Snackbar.LENGTH_LONG)
                        .setAction("COPY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData data = ClipData.newPlainText("Invite code", mExtras.getString("invite_code"));
                                manager.setPrimaryClip(data);
                                Snackbar.make(mCoordinatorLayout, "Invite code copied to clipboard.", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
    }

    private void initializeListeners() {
        /*mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transactionsIntent = new Intent(LobbyActivity.this, TransactionsActivity.class);
                startActivity(transactionsIntent);
            }
        });*/

        mSheetBehavior = BottomSheetBehavior.from(mLayoutBottomSheet);

        mSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        mBtnBottomSheet.setText("Close history transactions");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        mBtnBottomSheet.setText("History transactions");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        mBtnBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    mSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    mBtnBottomSheet.setText("Close history transactions");
                } else {
                    mSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBtnBottomSheet.setText("History transactions");
                }
            }
        });
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.lobby_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lobby \"" + mExtras.getString("lobby_name") + "\"");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCoordinatorLayout = findViewById(R.id.lobby_coordinator_layout_bottom_sheet);
        mAccountName = findViewById(R.id.lobby_account_name);
        mWalletName = findViewById(R.id.lobby_wallet_name);
        mWalletBalance = findViewById(R.id.lobby_wallet_balance);
        mRecyclerView = findViewById(R.id.lobby_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PlayerLobbyRecyclerAdapter(LobbyActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdmin = findViewById(R.id.lobby_admin);
        //mHistoryButton = findViewById(R.id.lobby_history_button);
        mProgressBar = findViewById(R.id.lobby_progress_bar);

        //BottomSheet


        mLayoutBottomSheet = findViewById(R.id.bottom_sheet);
        mBtnBottomSheet = findViewById(R.id.btn_bottom_sheet);
        mRecyclerViewBottobSheet = findViewById(R.id.transactions_recycler_view_1);
        mLayoutManagerBottobSheet = new LinearLayoutManager(this);
        mRecyclerViewBottobSheet.setLayoutManager(mLayoutManagerBottobSheet);
        mAdapterBottomSheet = new TransactionRecyclerAdapter();
        mRecyclerViewBottobSheet.setAdapter(mAdapterBottomSheet);
    }


    private void downloadLobby() {
        loading(true);
        DocumentReference ref = FirebaseFirestore.getInstance()
                .collection("lobbies")
                .document(mExtras.getString("lobby_id"));
        ref.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Lobby lobby = new Lobby();
                            lobby.setAdminId(document.getString("admin_id"));
                            lobby.setCreatedAt(document.getTimestamp("created_at"));
                            lobby.setGo(Integer.parseInt(document.getString("go")));
                            lobby.setIncome(Integer.parseInt(document.getString("income")));
                            lobby.setLuxury(Integer.parseInt(document.getString("luxury")));
                            lobby.setName(document.getString("name"));
                            lobby.setInitBalance(Integer.parseInt(document.getString("init_balance")));
                            lobby.setInviteCode(document.getString("invite_code"));
                            lobby.setAdminId(document.getString("admin_id"));
                            MBank.setLobby(lobby);
                            downloadWallets();
                        }
                    }
                });
    }

    private void loading(boolean loading) {
        mCoordinatorLayout.setBackground(getDrawable(loading ? R.color.white : R.color.transparent));
        mBtnBottomSheet.setEnabled(!loading);
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void downloadWallets() {
        CollectionReference ref = FirebaseFirestore.getInstance().collection("wallets");
        ref.whereEqualTo("lobby_id", mExtras.getString("lobby_id"))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            MBank.getLobby().getWallets().clear();
                            for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
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
                                else
                                    MBank.getLobby().getWallets().add(w);
                            }
                            updateCards();
                        }
                    }
                });
    }

    private void initializeMainCard(Wallet w) {
        mAccountName.setText(MBank.getUser().getName());
        mWalletName.setText(w.getName());
        mWalletBalance.setText(Integer.toString(w.getBalance()));
        mAdmin.setVisibility(MBank.getLobby().getAdminId().equals(w.getOwnerId()) ? View.VISIBLE : View.INVISIBLE);
    }

    private void updateCards() {
        MBank.getLobby().getWallets().sort(Wallet.BalanceComparator);
        mAdapter.notifyDataSetChanged();
        loading(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.lobby_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.lobby_menu_leave:
                Snackbar.make(findViewById(R.id.lobby_coordinator_layout_bottom_sheet), "Imagine you have left", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
