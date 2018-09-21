package com.welop.mbank.activities;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.welop.svlit.mbank.R;

public class CreateLobbyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button cancel;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        toolbar = findViewById(R.id.create_lobby_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create lobby");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancel = findViewById(R.id.create_lobby_cancel);
        start = findViewById(R.id.create_lobby_start);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
        }
        });
    }
}
