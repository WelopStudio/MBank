package com.welop.mbank.activities;

import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.welop.svlit.mbank.R;

public class RestoreActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText mEmail;
    private ImageView mClearAll;
    private Button mChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore);
        initializeViews();
        initializeListeners();
    }

    private void initializeListeners() {
        mClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail.getText().clear();
            }
        });

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){

                    mClearAll.setVisibility(View.VISIBLE);
                    mChangePassword.setVisibility(View.VISIBLE);
                } else {
                    mClearAll.setVisibility(View.INVISIBLE);
                    mChangePassword.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.restore_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Restore");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        mClearAll = findViewById(R.id.restore_close_image_view);
        mChangePassword = findViewById(R.id.restore_change_password_button);
        mEmail = findViewById(R.id.restore_edit_text_email);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }


}
