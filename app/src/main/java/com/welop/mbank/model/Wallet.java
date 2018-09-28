package com.welop.mbank.model;

public class Wallet {
    private String mName;
    private String mOwnerName;
    private String mOwnerId;
    private String mLobbyId;
    private String mLobbyName;
    private int mBalance;

    public Wallet() {
        this ("", "", "", "", "", 0);
    }

    public Wallet(String name, String ownerName, String ownerId, String lobbyId, String lobbyName, int balance) {
        this.mName = name;
        this.mOwnerName = ownerName;
        this.mOwnerId = ownerId;
        this.mLobbyId = lobbyId;
        this.mLobbyName = lobbyName;
        this.mBalance = balance;
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

    public int getBalance() {
        return mBalance;
    }

    public void setBalance(int balance) {
        this.mBalance = balance;
    }
}
