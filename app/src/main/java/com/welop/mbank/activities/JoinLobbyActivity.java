package com.welop.mbank.activities;

import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.welop.svlit.mbank.R;

public class JoinLobbyActivity extends AppCompatActivity {

    Toolbar mToolbar;
    private TextInputEditText mInviteCode;
    private Button mConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);
        initializeViews();
        initializeListeners();
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.join_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Join");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mInviteCode = findViewById(R.id.join_invite_code);
        mConnect = findViewById(R.id.join_join_btn);
    }

    private void initializeListeners() {
        mConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinLobbyActivity.this, CreateWalletActivity.class);
                intent.putExtra("ActivityName",JoinLobbyActivity.class.getSimpleName());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
