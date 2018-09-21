package com.welop.mbank.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Account class. Account is a real app user.
 */
public class Account {
    private String name;
    private String email;
    private String sex;
    private int passwordHashcode;
    private Map<Lobby, Wallet> wallets;
    private HashSet<Account> friends;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Returns account's name.
     * @return Account's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets account's name.
     * @param name New account's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns account's email.
     * @return Account's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets account's email.
     * @param email Account's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns account password hash code.
     * @return Account password hash code.
     */
    int getPasswordHashcode() {
        return passwordHashcode;
    }

    /**
     * All rooms and appropriate wallets of the account.
     * @return Map of Rooms and Wallets.
     */
    public Map<Lobby, Wallet> getWallets() {
        return wallets;
    }

    /**
     * Public constructor of an Account instance.
     * @param name Username that is visible for other accounts.
     * @param email An email connected to the account.
     * @param passwordHashcode Hash code of account password.
     */
    public Account(String name, String email, int passwordHashcode) {
        setName(name);
        setEmail(email);
        this.passwordHashcode = passwordHashcode;
        this.wallets = new HashMap<>();
        this.friends = new HashSet<>();
    }

    public Account() {}

    /**
     * Creates new room and sets its administrator equals to this account.
     * @param name Administrator's wallet name.
     * @return New room reference.
     */
    public Lobby createRoom(String name, GameSettings gameSettings) throws AlreadyJoinedException {
        return new Lobby(this, name, gameSettings);
    }

    /**
     * Adds friend to friend list.
     * @param friend New friend.
     */
    public void addFriend(Account friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
            friend.addFriend(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
