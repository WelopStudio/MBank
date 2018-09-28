package com.welop.mbank.model;

import java.util.ArrayList;

public class Lobby {
    private String name;
    private ArrayList<Wallet> wallets;
    private String adminId;

    public Lobby(String name, String adminId) {
        this.name = name;
        this.adminId = adminId;
        this.wallets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Wallet> getWallets() {
        return wallets;
    }

    public String getAdminId() {
        return adminId;
    }
}
