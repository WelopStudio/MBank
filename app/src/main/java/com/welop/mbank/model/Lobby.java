package com.welop.mbank.model;

import java.util.ArrayList;

public class Lobby {
    private String mName;
    private ArrayList<Wallet> mWallets;
    private String mAdminId;

    public Lobby(String name, String adminId) {
        this.mName = name;
        this.mAdminId = adminId;
        this.mWallets = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public ArrayList<Wallet> getWallets() {
        return mWallets;
    }

    public String getAdminId() {
        return mAdminId;
    }
}
