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

import petrov.kristiyan.colorpicker.ColorPicker;

public class CreateWalletActivity extends AppCompatActivity {

    private TextInputEditText mWalletName;
    private Button mChooseColor;
    private Button mCreateWallet;
    private ImageView mCircleColorImage;
    private int mColor;
    private Toolbar toolbar;

    private String lobbyId;
    private String lobbyName;
    private HashMap<String, Object> lobbySettings = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        lobbyId = this.getIntent().getExtras().getString("lobbyId");
        lobbyName = this.getIntent().getExtras().getString("lobbyName");

        toolbar = findViewById(R.id.create_wallet_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wallet settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mWalletName = findViewById(R.id.create_wallet_wallet_name);
        mChooseColor = findViewById(R.id.create_wallet_pick_color_btn);
        mCreateWallet = findViewById(R.id.create_wallet_btn);
        mCircleColorImage = findViewById(R.id.create_wallet_picked_color);

        mCircleColorImage.setVisibility(View.INVISIBLE);

        mChooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        mCreateWallet = findViewById(R.id.create_wallet_btn);
        mCreateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSettings();
            }
        });

    }

    private void getSettings() {
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("settings")
                .document(lobbyId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        lobbySettings.put("lobby_id", document.get("lobby_id")); // В перспективе надо убрать, ID настроек совпадает с ID комнаты
                        lobbySettings.put("balance", document.get("balance"));
                        lobbySettings.put("go", document.get("go"));
                        lobbySettings.put("income", document.get("income"));
                        lobbySettings.put("luxury", document.get("luxury"));
                        storageAddWallet();
                    }
                } else {
                    // Не получилось
                }
            }
        });
    }

    private boolean storageAddWallet() {
        if (lobbySettings.size() == 0)
            return false;

        final boolean[] success = new boolean[1];
        success[0] = true;
        Map<String, Object> data = new HashMap<>();
        data.put("owner_id", FirebaseAuth.getInstance().getUid());
        data.put("name", mWalletName.getText().toString());
        data.put("balance", lobbySettings.get("balance"));
        data.put("lobby_id", lobbySettings.get("lobby_id"));
        data.put("lobby_name", lobbyName);
        FirebaseFirestore.getInstance()
                .collection("wallets")
                .document(FirebaseAuth.getInstance().getUid() + "_" + lobbySettings.get("lobby_id"))
                .set(data)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        success[0] = false;
                    }
                });
        return success[0];
    }

    private void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#AC96B7");
        colors.add("#CA9CA9");
        colors.add("#94AC96");
        colors.add("#7ABACA");
        colors.add("#AE977C");
        colors.add("#CAAD50");

        colorPicker.setColors(colors)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        mCircleColorImage.setVisibility(View.VISIBLE);
                        mCircleColorImage.setColorFilter(color);
                        mColor = color;
                        mChooseColor.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Bundle extras = getIntent().getExtras();
        Intent intent = this.getIntent();
        //String activityName = extras.getString("ActivityName");
        String activityName = intent.getExtras().getString("ActivityName");
        /*Toast toast = Toast.makeText(getApplicationContext(),
                activityName, Toast.LENGTH_SHORT);
        toast.show();*/
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
