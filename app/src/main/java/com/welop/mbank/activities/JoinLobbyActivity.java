package com.welop.mbank.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.welop.svlit.mbank.R;

public class JoinLobbyActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextInputEditText mCodeOfLobby;
    private Button mConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);

        toolbar = findViewById(R.id.join_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Join");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCodeOfLobby = findViewById(R.id.join_code_of_lobby);
        mConnect = findViewById(R.id.join_join_btn);

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