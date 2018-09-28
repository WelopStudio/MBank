package com.welop.mbank.model;

public class Wallet {
    private String name;
    private String ownerId;
    private String lobbyId;
    private String lobbyName;
    private int balance;

    public Wallet() {
        this ("", "", "", "", 0);
    }

    public Wallet(String name, String ownerId, String lobbyId, String lobbyName, int balance) {
        this.name = name;
        this.ownerId = ownerId;
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.balance = balance;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
