package com.welop.mbank.activities;

import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.welop.mbank.MBank;
import com.welop.svlit.mbank.R;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

public class JoinLobbyActivity extends AppCompatActivity {

    Toolbar mToolbar;
    private TextInputEditText mInviteCode;
    private Button mConnect;
    private TextInputEditText mWalletName;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);
        initializeViews();
        initializeListeners();
        loading(false);
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.join_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Join");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mInviteCode = findViewById(R.id.join_lobby_invite_code);
        mConnect = findViewById(R.id.join_join_btn);
        mWalletName = findViewById(R.id.join_lobby_wallet_name);
        mCoordinatorLayout = findViewById(R.id.join_lobby_coordinator_layout);
    }

    private void initializeListeners() {
        mConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading(true);
                findLobby(mInviteCode.getText().toString());
            }
        });
    }

    private void loading(boolean loading) {

    }

    private void findLobby(String inviteCode) {
        CollectionReference ref = FirebaseFirestore.getInstance().collection("lobbies");
        ref.whereEqualTo("invite_code", inviteCode)
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot q = task.getResult();
                    if (q.size() != 1) {
                        Snackbar.make(mCoordinatorLayout, "Error", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    else {
                        DocumentSnapshot d = q.getDocuments().get(0);
                        uploadWallet(d);
                    }
                }
            }
        });

    }

    private void uploadWallet(final DocumentSnapshot lobby) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("balance", lobby.getString("init_balance"));
        values.put("lobby_id", lobby.getId());
        values.put("lobby_name", lobby.getString("name"));
        values.put("name", mWalletName.getText().toString());
        values.put("owner_id", FirebaseAuth.getInstance().getUid());
        values.put("owner_name", MBank.getUser().getName());
        values.put("admin", false);
        values.put("created_at", new com.google.firebase.Timestamp(new Timestamp(new Date().getTime())));
        FirebaseFirestore.getInstance()
                .collection("wallets")
                .document(FirebaseAuth.getInstance().getUid() + "_" + lobby.getId())
                .set(values)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startLobby(lobby);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Snackbar.make(mCoordinatorLayout, "Error", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void startLobby(DocumentSnapshot lobby) {
        loading(false);
        Bundle extras = new Bundle();
        extras.putString("lobby_id", lobby.getId());
        extras.putString("lobby_name", lobby.getString("name"));
        extras.putString("invite_code", mInviteCode.getText().toString());
        Intent lobbyIntent = new Intent(JoinLobbyActivity.this, LobbyActivity.class);
        lobbyIntent.putExtras(extras);
        startActivity(lobbyIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
