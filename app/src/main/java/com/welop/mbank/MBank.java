package com.welop.mbank;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.welop.mbank.model.Account;
import com.welop.mbank.model.Wallet;

import java.util.ArrayList;
import java.util.HashMap;

public class MBank extends Application {
    private static ArrayList<Wallet> userWallets;
    private static Account user;
    private static ArrayList<Wallet> lobbyWallets;

    public static ArrayList<Wallet> getLobbyWallets() {
        return lobbyWallets;
    }

    public static Account getUser() {
        return user;
    }

    public static void setUser(Account user) {
        MBank.user = user;
    }

    public static ArrayList<Wallet> getUserWallets() {
        return userWallets;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);

        user = new Account();
        userWallets = new ArrayList<>();
        lobbyWallets = new ArrayList<>();
    }
}
