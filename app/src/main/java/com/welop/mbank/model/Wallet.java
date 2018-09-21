package com.welop.mbank.model;

import java.awt.*;

/**
 * Profile of an account for concrete lobby.
 */
public class Wallet {
    private Account owner;
    private String name; // "Cat", "Hat", "Plane", etc.
    private int balance;
    private Boolean isOnline;
    private String color;

    /**
     * Returns wallet color.
     * @return Wallet color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets wallet color.
     * @param color New wallet color.
     */
    void setColor(String color) {
        this.color = color;
    }

    @Deprecated
    public Boolean getOnline() {
        return isOnline;
    }

    @Deprecated
    void setOnline(Boolean online) {
        isOnline = online;
    }

    /**
     * Returns the wallet owner Account.
     * @return The wallet owner Account.
     */
    Account getOwner() {
        return owner;
    }

    /**
     * Returns wallet name.
     * @return Wallet name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns balance at moment.
     * @return Balance.
     */
    int getBalance() {
        return balance;
    }

    /**
     * Sets new balance. Used to setup Wallet.
     * @param balance New balance value.
     */
    void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Public constructor.
     * @param account Account owner.
     * @param name The name of this wallet ("Cat", "Hat", "Plane", etc.).
     * @param color Wallet color.
     */
    Wallet(Account account, String name, String color) {
        this.owner = account;
        this.name = name;
        this.isOnline = true;
        this.color = color;
    }

    /**
     * Transfers an amount of money from this wallet to stated.
     * @param to The receiver wallet.
     * @param amount Amount to be transferred.
     * @throws WithdrawException Throws when try to transfer more money than it is available.
     */
    void transfer(Wallet to, int amount) throws WithdrawException, NonpositiveAmountException {
        withdraw(amount);
        to.deposit(amount);
    }

    /**
     * Withdraws an amount of money from this wallet.
     * @param amount Amount to be withdrawn.
     * @throws WithdrawException Throws when try to transfer more money than it is available.
     */
    void withdraw(int amount) throws WithdrawException, NonpositiveAmountException {
        if (amount <= 0)
            throw new NonpositiveAmountException(amount);
        if (amount > balance)
            throw new WithdrawException(this, amount - balance);
        balance -= amount;
    }

    /**
     * Deposits an amount of money to this wallet.
     * @param amount Amount to be added.
     */
    void deposit(int amount) throws NonpositiveAmountException {
        if (amount <= 0)
            throw new NonpositiveAmountException(amount);
        balance += amount;
    }

    /**
     * Returns full name of the wallet.
     * @return Full name consisting of Account name and this wallet name.
     */
    @Override
    public String toString() {
        return owner + " (" + name + "): $" + getBalance();
    }
}
