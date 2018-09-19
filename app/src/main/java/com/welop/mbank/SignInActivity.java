package com.welop.mbank;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.welop.svlit.mbank.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "SignIn";

    private Button mCancel;
    private Button mLogin;
    private TextView mForgot;

    private TextView mEmail;
    private TextView mPassword;

    private String mHashPassword;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Views
        mEmail = findViewById(R.id.login_email);
        mPassword =findViewById(R.id.login_password);

        progressBar = findViewById(R.id.signin_progress_bar);


        //Buttons
        mLogin = findViewById(R.id.login_button_login);
        mLogin.setOnClickListener(this);
        mCancel = findViewById(R.id.login_button_cancel);
        mCancel.setOnClickListener(this);
        mForgot = findViewById(R.id.login_forgot_text);
        mForgot.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void signIn(String email, String password){
        Log.d(TAG, "signIn:" + email);

        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(ProgressBar.VISIBLE);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            /*Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*/
                            Snackbar.make(findViewById(R.id.login_constraintlayout), "Authentication failed.", Snackbar.LENGTH_SHORT).show();

                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //
                        }

                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]


    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
        } else {
            //
        }
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
        if (TextUtils.isEmpty(email)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == mLogin.getId()){
            mHashPassword = mPassword.getText().toString();
            signIn(mEmail.getText().toString(),mHashPassword);
        } else if (i == mCancel.getId()) {
            finish();
        } else if (i == mForgot.getId()){
            //TODO hash
            Intent intent = new Intent(SignInActivity.this, ForgotActivity.class);
            startActivity(intent);
        }
    }
}
