package com.welop.mbank.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Lobby {
    private String mName;
    private ArrayList<Wallet> mWallets;
    private String mAdminId;
    private Timestamp mCreatedAt;
    private int go;
    private int income;
    private int initBalance;
    private int luxury;
    private String adminId;
    private String mInviteCode;

    public Lobby() {
        mWallets = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<Wallet> getWallets() {
        return mWallets;
    }

    public void setWallets(ArrayList<Wallet> wallets) {
        mWallets = wallets;
    }

    public String getAdminId() {
        return mAdminId;
    }

    public void setAdminId(String adminId) {
        mAdminId = adminId;
    }

    public Timestamp getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        mCreatedAt = createdAt;
    }

    public int getGo() {
        return go;
    }

    public void setGo(int go) {
        this.go = go;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getInitBalance() {
        return initBalance;
    }

    public void setInitBalance(int initBalance) {
        this.initBalance = initBalance;
    }

    public int getLuxury() {
        return luxury;
    }

    public void setLuxury(int luxury) {
        this.luxury = luxury;
    }

    public void setInviteCode(String inviteCode) {
        mInviteCode = inviteCode;
    }

    public String getInviteCode() {
        return mInviteCode;
    }
}
