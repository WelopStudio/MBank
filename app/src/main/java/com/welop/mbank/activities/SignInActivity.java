package com.welop.mbank.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.welop.svlit.mbank.R;

public class SignInActivity extends AppCompatActivity {

    public static final String TAG = "SignIn";

    private Button mCancel;
    private Button mLogin;
    private TextView mForgot;
    private TextView mEmail;
    private TextView mPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initializeViews();
        initializeListeners();
    }

    private void initializeListeners() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(mEmail.getText().toString(),mPassword.getText().toString());
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RestoreActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        mEmail = findViewById(R.id.signin_email);
        mPassword =findViewById(R.id.signin_password);
        progressBar = findViewById(R.id.signin_progress_bar);
        // TODO: ???
        progressBar
                .getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
        mLogin = findViewById(R.id.signin_button_sign_in);
        mCancel = findViewById(R.id.signin_button_cancel);
        mForgot = findViewById(R.id.signin_forgot_text);
    }

    private void signIn(String email, String password){

        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(ProgressBar.VISIBLE);

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Snackbar.make(findViewById(R.id.login_constraintlayout), "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                });
    }

    private boolean validateForm(){
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

}
