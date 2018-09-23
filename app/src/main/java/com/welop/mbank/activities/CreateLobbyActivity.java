package com.welop.mbank.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.svlit.mbank.R;

import java.util.HashMap;
import java.util.Map;
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

        //View
        mCreateLobby = findViewById(R.id.create_lobby_button);
        mLobbyName = findViewById(R.id.create_lobby_lobby_name);
        mGoCost = findViewById(R.id.create_lobby_go_cost);
        mIncomeTaxCost = findViewById(R.id.create_lobby_income_tax_cost);
        mLuxuryTaxCost = findViewById(R.id.create_lobby_luxury_tax_come);
        mStartBalance = findViewById(R.id.create_lobby_start_balance);
        mCheckBoxShowOpponentsBalance =
                findViewById(R.id.create_lobby_checkBox_show_opponents_balance);

        mCreateLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 0. Если все поля пусты, настройки по умолчанию. Если заполнено не все, требовать заполнить все.
                // Пока считаем, что заполнено все.
                UUID id = UUID.randomUUID();

                // 1. Создать настройки (или подключить default)
                if (storageAddSettings(id))
                    // 2. Создать комнату
                    if (storageAddLobby(id)) {
                        // 3. Создать кошелек (перейти в другую активность)
                        Intent intent = new Intent(CreateLobbyActivity.this, CreateWalletActivity.class);
                        intent.putExtra("ActivityName", CreateLobbyActivity.class.getSimpleName());
                        intent.putExtra("lobbyId", id.toString());
                        intent.putExtra("lobbyName", mLobbyName.getText().toString());
                        startActivity(intent);
                    }
                    else {
                        // Комната не создалась
                    }
                else {
                    // Настройки не создались
                }

            }
        });
    }

    private boolean storageAddSettings(UUID id) {
        final boolean[] success = new boolean[1];
        success[0] = true;
        Map<String, Object> data = new HashMap<>();
        data.put("lobby_id", id.toString()); // В перспективе надо убрать, ID настроек совпадает с ID комнаты
        data.put("balance", mStartBalance.getText().toString());
        data.put("go", mGoCost.getText().toString());
        data.put("income", mIncomeTaxCost.getText().toString());
        data.put("luxury", mLuxuryTaxCost.getText().toString());
        FirebaseFirestore.getInstance()
                .collection("settings")
                .document(id.toString())
                .set(data)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        success[0] = false;
                    }
                });
        return success[0];
    }

    private boolean storageAddLobby(UUID id) {
        final boolean[] success = new boolean[1];
        success[0] = true;
        Map<String, Object> data = new HashMap<>();
        data.put("admin_id", FirebaseAuth.getInstance().getUid());
        data.put("lobby_id", id.toString());
        data.put("settings_id", id.toString());
        FirebaseFirestore.getInstance()
                .collection("lobbies")
                .document(id.toString())
                .set(data)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        success[0] = false;
                    }
                });
        return success[0];
    }
}
