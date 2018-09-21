package com.welop.mbank.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Game room class. This is where several wallets do play Monopoly-like games but not Monopoly.
 */
public class Lobby {
    private Account administrator;
    private HashMap<Account, Wallet> wallets;
    private final GameSettings gameSettings;
    private TransactionManager transactionManager;
    private Boolean isActive;

    /**
     * Returns whether lobby is active or not.
     * @return Whether lobby is active or not.
     */
    public Boolean getActive() {
        for (Wallet w: getWallets().values()) {
            if (!w.getOnline())
                return false;
        }
        return true;
    }

    /**
     * Returns current room administrator reference.
     * @return Current administrator.
     */
    public Account getAdministrator() {
        return administrator;
    }

    /**
     * Sets new administrator.
     * @param administrator New administrator.
     */
    private void setAdministrator(Account administrator) {
        this.administrator = administrator;
    }

    /**
     * Returns game settings connected to this room.
     * @return Game settings connected to this room.
     */
    public GameSettings getGameSettings() {
        return gameSettings;
    }

    /**
     * Returns all wallets of the room.
     * @return ArrayList of wallets involved to current room.
     */
    public HashMap<Account, Wallet> getWallets() {
        return wallets;
    }

    /**
     * Adds new account to the room. If successful, also creates room wallet for account.
     * @param account Account of a player to createWalletForRoom.
     * @param name Wallet name for this room.
     */
    public void addAccount(Account account, String name) throws AlreadyJoinedException {
        if (getWallets().containsKey(account))
            throw new AlreadyJoinedException(account, this);
        Wallet wallet = new Wallet(account, name, "#CCCCCC");
        account.getWallets().put(this, wallet);
        getWallets().put(account, wallet);
        wallet.setBalance(gameSettings.getStartBalance());
    }

    /**
     * Returns lobby transaction manager.
     * @return Lobby transaction manager.
     */
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * Package-private constructor. Lobby can be created only by an Account instance in order to have administrator properly.
     * @param administrator Creator of the room.
     * @param name Administrator's wallet name.
     * @param gameSettings GameSettings instance to declare room's rules.
     */
    Lobby(Account administrator, String name, GameSettings gameSettings) throws AlreadyJoinedException {
        this.gameSettings = gameSettings;
        wallets = new HashMap<>();
        addAccount(administrator, name);
        setAdministrator(administrator);
        transactionManager = new TransactionManager(this);
    }

    public void deposit(Account to, int amount) throws LobbyInactiveException, NonpositiveAmountException {
        getTransactionManager().deposit(to, amount);
    }

    public void withdraw(Account from, int amount) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
        getTransactionManager().withdraw(from, amount);
    }

    public void transfer(Account from, Account to, int amount) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
        getTransactionManager().transfer(from, to, amount);
    }

    public void go(Account to) throws LobbyInactiveException, NonpositiveAmountException {
        getTransactionManager().go(to);
    }

    public void luxuryTax(Account from) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
        getTransactionManager().luxuryTax(from);
    }

    public void incomeTax(Account from) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
        getTransactionManager().incomeTax(from);
    }

    public void payEach(Account from, int amount) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
        getTransactionManager().payEach(from, amount);
    }

    public void collectFromEveryone(Account to, int amount) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
        getTransactionManager().collectFromEveryone(to, amount);
    }
}
