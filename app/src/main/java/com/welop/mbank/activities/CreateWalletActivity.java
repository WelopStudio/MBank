package com.welop.mbank.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.welop.svlit.mbank.R;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class CreateWalletActivity extends AppCompatActivity {

    private TextInputEditText mWalletName;
    private Button mChooseColor;
    private Button mCreateWallet;
    private ImageView mCircleColorImage;
    private int mColor;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

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
