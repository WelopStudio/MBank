package com.welop.mbank.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

    public static final String TAG = "SignUp";
    private FirebaseFirestore mStorage;

    private Button mSignUp;
    private Button mCancel;
    private TextView mEmail;
    private TextView mPassword;
    private TextView mName;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mStorage = FirebaseFirestore.getInstance();
        mEmail = findViewById(R.id.signup_email);
        mName = findViewById(R.id.signup_name);
        mPassword =findViewById(R.id.signup_password);
        mProgressBar = findViewById(R.id.signup_progress_bar);
        mSignUp = findViewById(R.id.signup_button_signup);
        mCancel = findViewById(R.id.signup_button_cancel);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Наверно тут как-то надо перадать имя?
                signUp(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void signUp(String email, String password){
        Log.d(TAG, "signUp: " + mEmail);

        if (!validateForm()) {
            return;
        }

        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() & storageAddAccount(mEmail.getText(), mName.getText())) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            updateUI(user);
                        } else {
                            Snackbar.make(findViewById(R.id.signup_constrainLayout), "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                });
    }

    /**
     * Создать документ аккаунта в Firestore
     * @param email
     * @param name
     * @return Выполнено ли добавление успешно
     */
    private boolean storageAddAccount(CharSequence email, CharSequence name) {
        final boolean[] success = new boolean[1];
        success[0] = true;
        Map<String, Object> data = new HashMap<>();
        data.put("name", name.toString());
        data.put("email", email.toString());
        data.put("sex", "not stated");
        data.put("description", "To be filled");
        mStorage.collection("accounts").document(FirebaseAuth.getInstance().getUid()).set(data)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        success[0] = false;
                    }
                });
        return success[0];
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

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
        } else {
            //TODO что тут? Где еще используется этот метод => нужен ли он?
        }
    }
}
