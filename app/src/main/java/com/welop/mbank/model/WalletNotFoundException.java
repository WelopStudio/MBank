package com.welop.mbank.model;

public class WalletNotFoundException extends Throwable {
    private Wallet wallet;
    private Lobby lobby;

    public Wallet getWallet() {
        return wallet;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public WalletNotFoundException(Wallet wallet, Lobby lobby) {
        this.wallet = wallet;
        this.lobby = lobby;
    }
}
