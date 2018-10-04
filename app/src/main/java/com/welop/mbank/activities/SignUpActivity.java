package com.welop.mbank.activities;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.svlit.mbank.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private Button mSignUp;
    private Button mCancel;
    private TextView mEmail;
    private TextView mPassword;
    private TextView mName;
    private ProgressBar mProgressBar;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeViews();
        initializeListeners();
    }

    private void initializeListeners() {
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {
        mEmail = findViewById(R.id.signup_email);
        mName = findViewById(R.id.signup_name);
        mPassword =findViewById(R.id.signup_password);
        mProgressBar = findViewById(R.id.signup_progress_bar);
        mSignUp = findViewById(R.id.signup_button_signup);
        mCancel = findViewById(R.id.signup_button_cancel);
        mCoordinatorLayout = findViewById(R.id.signup_coordinator_layout);
    }

    private void signUp(){
        if (!validateForm()) {
            return;
        }

        loading(true);

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadAccount();

                        } else {
                            loading(false);
                            Snackbar.make(mCoordinatorLayout, "Authentication failed.", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void uploadAccount() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", mName.getText().toString());
        data.put("email", mEmail.getText().toString());
        data.put("sex", "not stated");
        data.put("description", "To be filled");
        FirebaseFirestore.getInstance()
                .collection("accounts")
                .document(FirebaseAuth.getInstance().getUid())
                .set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        loading(false);
                        if (task.isSuccessful()) {
                            proceedToMainActivity();
                        }
                        else {
                            Snackbar.make(mCoordinatorLayout, "Authentication failed.", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void loading(boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void proceedToMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

    private boolean validateForm(){
        boolean valid = true;

        if (TextUtils.isEmpty(mEmail.getText().toString())) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }
        if (TextUtils.isEmpty(mName.getText().toString())) {
            mName.setError("Required.");
            valid = false;
        } else {
            mName.setError(null);
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }
}
