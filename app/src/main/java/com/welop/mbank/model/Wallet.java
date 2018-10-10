package com.welop.mbank.model;

import com.google.firebase.Timestamp;

import java.util.Comparator;

public class Wallet {
    public static Comparator<Wallet> DateComparator = new Comparator<Wallet>() {
        @Override
        public int compare(Wallet o1, Wallet o2) {
            return o1.mCreatedAt.compareTo(o2.mCreatedAt);
        }
    };

    public static Comparator<Wallet> BalanceComparator = new Comparator<Wallet>() {
        @Override
        public int compare(Wallet o1, Wallet o2) {
            return o1.mBalance > o2.mBalance ? 1 : (o1.mBalance == o2.mBalance ? 0 : -1);
        }
    };

    private String mId;
    private String mName;
    private String mOwnerName;
    private String mOwnerId;
    private String mLobbyId;
    private String mLobbyName;
    private Long mBalance;
    private Timestamp mCreatedAt;
    private boolean mIsAdmin;

    public Wallet(){
    }


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean isIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(boolean mIsAdmin) {
        this.mIsAdmin = mIsAdmin;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    public void setOwnerName(String ownerName) {
        mOwnerName = ownerName;
    }

    public String getLobbyName() {
        return mLobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.mLobbyName = lobbyName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(String ownerId) {
        this.mOwnerId = ownerId;
    }

    public String getLobbyId() {
        return mLobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.mLobbyId = lobbyId;
    }

    public Long getBalance() {
        return mBalance;
    }

    public void setBalance(Long balance) {
        this.mBalance = balance;
    }

    public Timestamp getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        mCreatedAt = createdAt;
    }
}
