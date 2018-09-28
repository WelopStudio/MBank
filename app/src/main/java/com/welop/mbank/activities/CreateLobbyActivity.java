package com.welop.mbank.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.welop.svlit.mbank.R;

import java.util.UUID;

public class CreateLobbyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button mCreateLobby;
    private TextInputEditText mLobbyName;
    private TextInputEditText mGoCost;
    private TextInputEditText mIncomeTaxCost;
    private TextInputEditText mLuxuryTaxCost;
    private TextInputEditText mStartBalance;
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
        mCreateLobby = findViewById(R.id.create_lobby_button);
        mLobbyName = findViewById(R.id.create_lobby_lobby_name);
        mGoCost = findViewById(R.id.create_lobby_go_cost);
        mIncomeTaxCost = findViewById(R.id.create_lobby_income_tax_cost);
        mLuxuryTaxCost = findViewById(R.id.create_lobby_luxury_tax_come);
        mStartBalance = findViewById(R.id.create_lobby_start_balance);
        mCheckBoxShowOpponentsBalance = findViewById(R.id.create_lobby_checkBox_show_opponents_balance);

        mCreateLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    startCreateWalletActivity(getData());
                }
            }
        });
    }

    /**
     * Checks fields and shows errors.
     * @return Check result.
     */
    private boolean checkFields() {
        // TODO: Check fields and show errors
        return true;
    }

    /**
     * Starts {@link CreateWalletActivity} with specified extras.
     * @param extras Extras bundle.
     */
    private void startCreateWalletActivity(Bundle extras) {
        Intent intent = new Intent(CreateLobbyActivity.this, CreateWalletActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * Places all needed data to a bundle to send it to {@link CreateWalletActivity}.
     * @return Extras bundle.
     */
    private Bundle getData() {
        Bundle data = new Bundle();
        data.putString("admin_id", FirebaseAuth.getInstance().getUid());
        data.putString("lobby_id", UUID.randomUUID().toString());
        data.putString("lobby_name", mLobbyName.getText().toString());
        data.putString("init_balance", mStartBalance.getText().toString());
        data.putString("go", mGoCost.getText().toString());
        data.putString("income", mIncomeTaxCost.getText().toString());
        data.putString("luxury", mLuxuryTaxCost.getText().toString());
        data.putString("ActivityName", CreateLobbyActivity.class.getSimpleName());
        return data;
    }
}
