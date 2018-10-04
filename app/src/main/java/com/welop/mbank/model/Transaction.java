package com.welop.mbank.model;

public class Transaction {
    private String mPayer;
    private String mReceiver;
    private int mAmount;

    public String getPayer() {
        return mPayer;
    }

    public void setPayer(String payer) {
        mPayer = payer;
    }

    public String getReceiver() {
        return mReceiver;
    }

    public void setReceiver(String receiver) {
        mReceiver = receiver;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public Transaction() {
    }

    public Transaction(String payer, String receiver, int amount) {
        mPayer = payer;
        mReceiver = receiver;
        mAmount = amount;
    }
}
