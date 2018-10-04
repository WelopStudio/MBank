package com.welop.mbank.activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
    private Button mCancel;
    private Button mSignIn;
    private TextView mForgot;
    private TextView mEmail;
    private TextView mPassword;
    private ProgressBar mProgressBar;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initializeViews();
        initializeListeners();
    }

    private void initializeListeners() {
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
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
        mProgressBar = findViewById(R.id.signin_progress_bar);
        mSignIn = findViewById(R.id.signin_button_sign_in);
        mCancel = findViewById(R.id.signin_button_cancel);
        mForgot = findViewById(R.id.signin_forgot_text);
        mCoordinatorLayout = findViewById(R.id.signin_coordinator_layout);
    }

    private void signIn(){

        if (!validateForm()) {
            return;
        }

        loading(true);

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loading(false);
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Snackbar.make(mCoordinatorLayout, "Authentication failed.", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void loading(boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
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
