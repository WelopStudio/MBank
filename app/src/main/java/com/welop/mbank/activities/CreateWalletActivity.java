package com.welop.mbank.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.svlit.mbank.R;

import java.util.HashMap;

public class CreateWalletActivity extends AppCompatActivity {

    private TextInputEditText mWalletName;
    private Button mCreateWallet;
    private Toolbar mToolbar;
    private Bundle mIntentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntentData = this.getIntent().getExtras();
        setContentView(R.layout.activity_create_wallet);


        mToolbar = findViewById(R.id.create_wallet_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Wallet settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mWalletName = findViewById(R.id.create_wallet_wallet_name);
        mCreateWallet = findViewById(R.id.create_wallet_btn);
        mCreateWallet = findViewById(R.id.create_wallet_btn);
        mCreateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    uploadSettings();
                }
            }
        });

    }

    private boolean checkFields() {
        return true;
    }

    private void uploadSettings() {
        HashMap<String, String> settings = new HashMap<>();
        settings.put("init_balance", mIntentData.getString("init_balance"));
        settings.put("go", mIntentData.getString("go"));
        settings.put("income", mIntentData.getString("income"));
        settings.put("luxury", mIntentData.getString("luxury"));
        FirebaseFirestore.getInstance()
                .collection("settings")
                .document(mIntentData.getString("lobby_id"))
                .set(settings)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadLobby();
                    }
        });
    }

    private void uploadLobby() {
        HashMap<String, String> lobby = new HashMap<>();
        lobby.put("admin_id", mIntentData.getString("admin_id"));
        FirebaseFirestore.getInstance()
                .collection("lobbies")
                .document(mIntentData.getString("lobby_id"))
                .set(lobby)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadWallet();
                    }
                });
    }

    private void uploadWallet() {
        HashMap<String, String> wallet = new HashMap<>();
        wallet.put("balance", mIntentData.getString("init_balance"));
        wallet.put("lobby_id", mIntentData.getString("lobby_id"));
        wallet.put("lobby_name", mIntentData.getString("lobby_name"));
        wallet.put("name", mWalletName.getText().toString());
        wallet.put("owner_id", FirebaseAuth.getInstance().getUid());
        FirebaseFirestore.getInstance()
                .collection("wallets")
                .document(FirebaseAuth.getInstance().getUid() + "_" + mIntentData.getString("lobby_id"))
                .set(wallet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startLobby();
                    }
                });
    }

    private void startLobby() {
        onSupportNavigateUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        String activityName = mIntentData.getString("ActivityName");
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
