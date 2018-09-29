package com.welop.mbank.activities;

import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.mbank.MBank;
import com.welop.svlit.mbank.R;

import java.util.HashMap;

public class CreateWalletActivity extends AppCompatActivity {

    private TextInputEditText mWalletName;
    private Button mCreateWallet;
    private Toolbar mToolbar;
    private Bundle mExtras;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExtras = this.getIntent().getExtras();
        setContentView(R.layout.activity_create_wallet);
        initializeViews();
        initializeListeners();
    }

    private void initializeListeners() {
        mCreateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    uploadSettings();
                }
            }
        });
    }

    private void initializeViews() {
        mProgressBar = findViewById(R.id.create_wallet_progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mToolbar = findViewById(R.id.create_wallet_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Wallet settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mWalletName = findViewById(R.id.create_wallet_wallet_name);
        mCreateWallet = findViewById(R.id.create_wallet_btn);
        mCreateWallet = findViewById(R.id.create_wallet_btn);
    }

    private boolean checkFields() {
        return true;
    }

    private void uploadSettings() {
        mProgressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> settings = new HashMap<>();
        settings.put("init_balance", mExtras.getString("init_balance"));
        settings.put("go", mExtras.getString("go"));
        settings.put("income", mExtras.getString("income"));
        settings.put("luxury", mExtras.getString("luxury"));
        FirebaseFirestore.getInstance()
                .collection("settings")
                .document(mExtras.getString("lobby_id"))
                .set(settings)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        uploadLobby();
                    }
        });
    }

    private void uploadLobby() {
        mProgressBar.setVisibility(View.VISIBLE);
        mExtras.putString("invite_code", generateInviteCode());
        HashMap<String, String> lobby = new HashMap<>();
        lobby.put("admin_id", mExtras.getString("admin_id"));
        lobby.put("invite_code", mExtras.getString("invite_code"));
        FirebaseFirestore.getInstance()
                .collection("lobbies")
                .document(mExtras.getString("lobby_id"))
                .set(lobby)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        uploadWallet();
                    }
                });
    }

    private String generateInviteCode() {
        return mExtras.getString("lobby_id").substring(0, 5).toUpperCase();
    }

    private void uploadWallet() {
        mProgressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> wallet = new HashMap<>();
        wallet.put("balance", mExtras.getString("init_balance"));
        wallet.put("lobby_id", mExtras.getString("lobby_id"));
        wallet.put("lobby_name", mExtras.getString("lobby_name"));
        wallet.put("name", mWalletName.getText().toString());
        wallet.put("owner_id", FirebaseAuth.getInstance().getUid());
        wallet.put("owner_name", MBank.getUser().getName());
        FirebaseFirestore.getInstance()
                .collection("wallets")
                .document(FirebaseAuth.getInstance().getUid() + "_" + mExtras.getString("lobby_id"))
                .set(wallet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        startLobby();
                    }
                });
    }

    private void startLobby() {
        Intent lobbyIntent = new Intent(CreateWalletActivity.this, LobbyActivity.class);
        Bundle extras = new Bundle();
        extras.putBoolean("just_created", true);
        extras.putString("invite_code", mExtras.getString("invite_code"));
        extras.putString("lobby_id", mExtras.getString("lobby_id"));
        extras.putString("lobby_name", mExtras.getString("lobby_name"));
        lobbyIntent.putExtras(extras);
        startActivity(lobbyIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        String activityName = mExtras.getString("ActivityName");
        if (activityName.equals(JoinLobbyActivity.class.getSimpleName())){
            Intent intent1 = new Intent(CreateWalletActivity.this ,MainActivity.class);
            startActivity(intent1);
            finishAffinity();
        } else if (activityName.equals(CreateLobbyActivity.class.getSimpleName())){
            onBackPressed();
        }
        return true;
    }

}
