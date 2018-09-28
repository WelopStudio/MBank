package com.welop.mbank.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.mbank.MBank;
import com.welop.svlit.mbank.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateWalletActivity extends AppCompatActivity {

    private TextInputEditText mWalletName;
    private Button mCreateWallet;
    private int mColor;
    private Toolbar toolbar;

    private String lobbyId;
    private String lobbyName;
    private HashMap<String, Object> lobbySettings = new HashMap<>();
    private Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = this.getIntent().getExtras();
        setContentView(R.layout.activity_create_wallet);


        toolbar = findViewById(R.id.create_wallet_toolbar);
        setSupportActionBar(toolbar);
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
        settings.put("init_balance", data.getString("init_balance"));
        settings.put("go", data.getString("go"));
        settings.put("income", data.getString("income"));
        settings.put("luxury", data.getString("luxury"));
        FirebaseFirestore.getInstance()
                .collection("settings")
                .document(data.getString("lobby_id"))
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
        lobby.put("admin_id", data.getString("admin_id"));
        FirebaseFirestore.getInstance()
                .collection("lobbies")
                .document(data.getString("lobby_id"))
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
        wallet.put("balance", data.getString("init_balance"));
        wallet.put("lobby_id", data.getString("lobby_id"));
        wallet.put("lobby_name", data.getString("lobby_name"));
        wallet.put("name", data.getString("name"));
        wallet.put("owner_id", data.getString("owner_id"));
        FirebaseFirestore.getInstance()
                .collection("wallets")
                .document(FirebaseAuth.getInstance().getUid() + "_" + data.getString("lobby_id"))
                .set(wallet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startLobby();
                    }
                });
    }

    private void startLobby() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = this.getIntent();
        String activityName = intent.getExtras().getString("ActivityName");
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
