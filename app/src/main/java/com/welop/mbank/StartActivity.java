package com.welop.mbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.welop.svlit.mbank.R;

public class StartActivity extends BaseActivity {

    private Button logIn;
    private Button signUp;
    private Button test2;

    // Yuri pidor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        logIn = findViewById(R.id.start_button_already_have_account);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        signUp = findViewById(R.id.start_button_create_new_account);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onStop() {
        super.onStop();
    }


}
