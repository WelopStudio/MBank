package com.welop.mbank.activities;

import android.content.Intent;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.mbank.MBank;
import com.welop.svlit.mbank.R;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class CreateLobbyActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mCreateLobby;
    private TextInputEditText mLobbyName;
    private TextInputEditText mGoCost;
    private TextInputEditText mIncomeTaxCost;
    private TextInputEditText mLuxuryTaxCost;
    private TextInputEditText mStartBalance;
    private TextInputEditText mWalletName;
    private ProgressBar mProgressBar;
    private CoordinatorLayout mCoordinatorLayout;

    private String lobbyId, inviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);
        initializeViews();
        initializeListeners();
        loading(false);
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.create_lobby_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create lobby");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);
        mCreateLobby = findViewById(R.id.create_lobby_button);
        mLobbyName = findViewById(R.id.create_lobby_lobby_name);
        mWalletName = findViewById(R.id.create_lobby_wallet_name);
        mGoCost = findViewById(R.id.create_lobby_go_cost);
        mIncomeTaxCost = findViewById(R.id.create_lobby_income_tax_cost);
        mLuxuryTaxCost = findViewById(R.id.create_lobby_luxury_tax_come);
        mStartBalance = findViewById(R.id.create_lobby_start_balance);
        mProgressBar = findViewById(R.id.create_lobby_progress_bar);
        mProgressBar.setIndeterminate(true);
        mCoordinatorLayout = findViewById(R.id.create_lobby_coordinator_layout);
    }

    private void initializeListeners() {
        mCreateLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    loading(true);
                    uploadLobby();
                }
            }
        });
    }

    private void loading(boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    /**
     * Checks fields and shows errors.
     * @return Check result.
     */
    private boolean checkFields() {
        // TODO: Check fields and show errors
        return true;
    }

    private void uploadLobby() {
        loading(true);
        lobbyId = UUID.randomUUID().toString();
        inviteCode = lobbyId.substring(0, 5).toUpperCase();
        HashMap<String, Object> values = new HashMap<>();
        values.put("admin_id", FirebaseAuth.getInstance().getUid());
        values.put("name", mLobbyName.getText().toString());
        values.put("invite_code", inviteCode);
        values.put("init_balance", mStartBalance.getText().toString());
        values.put("go", mGoCost.getText().toString());
        values.put("income", mIncomeTaxCost.getText().toString());
        values.put("luxury", mLuxuryTaxCost.getText().toString());
        values.put("created_at", new com.google.firebase.Timestamp(new Timestamp(new Date().getTime())));
        FirebaseFirestore.getInstance()
                .collection("lobbies")
                .document(lobbyId)
                .set(values)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        uploadWallet();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        loading(false);
                        Snackbar.make(mCoordinatorLayout, "Error", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void uploadWallet() {
        mProgressBar.setVisibility(View.VISIBLE);
        HashMap<String, Object> values = new HashMap<>();
        values.put("balance", mStartBalance.getText().toString());
        values.put("lobby_id", lobbyId);
        values.put("lobby_name", mLobbyName.getText().toString());
        values.put("name", mWalletName.getText().toString());
        values.put("owner_id", FirebaseAuth.getInstance().getUid());
        values.put("owner_name", MBank.getUser().getName());
        values.put("created_at", new com.google.firebase.Timestamp(new Timestamp(new Date().getTime())));
        FirebaseFirestore.getInstance()
                .collection("wallets")
                .document(FirebaseAuth.getInstance().getUid() + "_" + lobbyId)
                .set(values)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        startLobby();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        loading(false);
                        Snackbar.make(mCoordinatorLayout, "Error", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void startLobby() {
        Intent lobbyIntent = new Intent(CreateLobbyActivity.this, LobbyActivity.class);
        Bundle extras = new Bundle();
        extras.putBoolean("just_created", true);
        extras.putString("invite_code", inviteCode);
        extras.putString("lobby_id", lobbyId);
        extras.putString("lobby_name", mLobbyName.getText().toString());
        lobbyIntent.putExtras(extras);
        startActivity(lobbyIntent);
        finish();
    }
}
