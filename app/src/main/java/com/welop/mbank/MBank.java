package com.welop.mbank;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.welop.mbank.model.Account;
import com.welop.mbank.model.Lobby;
import com.welop.mbank.model.Wallet;

import java.util.ArrayList;

public class MBank extends Application {
    private static ArrayList<Wallet> userWallets;
    private static Account user;
    private static Lobby lobby;
    private static Wallet wallet;

    public static Wallet getWallet() {
        return wallet;
    }

    public static void setWallet(Wallet wallet) {
        MBank.wallet = wallet;
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

    public static Lobby getLobby() {
        return lobby;
    }

    public static void setLobby(Lobby lobby) {
        MBank.lobby = lobby;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);

        user = new Account();
        lobby = new Lobby();
        userWallets = new ArrayList<>();
    }
}
