package com.welop.mbank;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.welop.mbank.model.Account;
import com.welop.mbank.model.Lobby;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class MBank extends Application {

    static Account account;

    static {
        account = new Account();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);
    }

    public static Account getAccount() {
        return account;
    }

    public static boolean setAccount(DocumentSnapshot document) {
        MBank.getAccount().setName(document.getString("name"));
        MBank.getAccount().setEmail(document.getString("email"));
        MBank.getAccount().setSex(document.getString("sex"));
        return true;
    }
}
