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
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.welop.svlit.mbank.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends BaseActivity implements View.OnClickListener{

    public static final String TAG = "SignUp";

    private Button mSignUp;
    private Button mCancel;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView mEmail;
    private TextView mPassword;
    private TextView mPassword2;
    private TextView mName;

    private String mHashPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        //Views
        mEmail = findViewById(R.id.signup_email);
        mPassword =findViewById(R.id.signup_password);
        mPassword2 =findViewById(R.id.signup_password2);

        // Buttons
        mSignUp = findViewById(R.id.signup_button_signup);
        mSignUp.setOnClickListener(this);
        mCancel = findViewById(R.id.signup_button_cancel);
        mCancel.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void signUp(final String email, String password){
        Log.d(TAG, "signUp: " + mEmail);

        if (!validateForm()) {

            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", "Test name");
                            userData.put("email", email);
                            db.collection("accounts").add(userData)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Snackbar.make(findViewById(R.id.signup_constrainLayout), "Authentication failed.", Snackbar.LENGTH_SHORT).show();

                            /*Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*/

                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]

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

        String password2 = mPassword2.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mPassword2.setError("Required.");
            valid = false;
        } else {
            mPassword2.setError(null);
        }

        if (!(password.equals(password2))) {
            Snackbar.make(findViewById(R.id.signup_constrainLayout), "Passwords do not match.", Snackbar.LENGTH_SHORT).show();
            mPassword.setError("Required.");
            mPassword2.setError("Required.");
            valid = false;

        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
        } else {
            //
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signup_button_cancel) {
            finish();
        } else if (i == R.id.signup_button_signup){
            //TODO hash
            mHashPassword = mPassword.getText().toString();
            signUp(mEmail.getText().toString(),mHashPassword);
        }
    }
}
