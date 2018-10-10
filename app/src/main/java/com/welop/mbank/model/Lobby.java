package com.welop.mbank.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Random;

public class Lobby {
    private String mName;
    private ArrayList<Wallet> mWallets;
    private ArrayList<Transaction> mTransactions;
    private String mAdminId;
    private Timestamp mCreatedAt;
    private Long go;
    private Long income;
    private Long initBalance;
    private Long luxury;
    private String mInviteCode;
    private String mId;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public ArrayList<Transaction> getTransactions() {
        return mTransactions;
    }

    public Lobby() {
        mWallets = new ArrayList<>();
        mTransactions = new ArrayList<>();
        //fillWithTestValues();
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

    public Long getGo() {
        return go;
    }

    public void setGo(Long go) {
        this.go = go;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public Long getInitBalance() {
        return initBalance;
    }

    public void setInitBalance(Long initBalance) {
        this.initBalance = initBalance;
    }

    public Long getLuxury() {
        return luxury;
    }

    public void setLuxury(Long luxury) {
        this.luxury = luxury;
    }

    public void setInviteCode(String inviteCode) {
        mInviteCode = inviteCode;
    }

    public String getInviteCode() {
        return mInviteCode;
    }
}
