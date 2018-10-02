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

    private void initializeListeners() {
    }

    private boolean checkFields() {
        return true;
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
