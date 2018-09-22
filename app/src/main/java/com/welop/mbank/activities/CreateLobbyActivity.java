package com.welop.mbank.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.welop.svlit.mbank.R;

public class CreateLobbyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button mCreateLobby;
    private TextInputEditText mLobbyName;
    private TextInputEditText mGoCost;
    private TextInputEditText mIncomeTaxCost;
    private TextInputEditText mLuxuryTaxCost;
    private CheckBox mCheckBoxShowOpponentsBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        toolbar = findViewById(R.id.create_lobby_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create lobby");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        //View
        mCreateLobby = findViewById(R.id.create_lobby_button);
        mLobbyName = findViewById(R.id.create_lobby_lobby_name);
        mGoCost = findViewById(R.id.create_lobby_go_cost);
        mIncomeTaxCost = findViewById(R.id.create_lobby_income_tax_cost);
        mLuxuryTaxCost = findViewById(R.id.create_lobby_luxury_tax_come);
        mCheckBoxShowOpponentsBalance =
                findViewById(R.id.create_lobby_checkBox_show_opponents_balance);

        mCreateLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateLobbyActivity.this, CreateWalletActivity.class);
                startActivity(intent);

            }
        });
    }
}
