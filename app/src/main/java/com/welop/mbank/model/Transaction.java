package com.welop.mbank.model;

public class Transaction {
    private String mPayerWalletName;
    private String mReceiverWalletName;
    private String mPayerAccountName;
    private String mReceiverAccountName;
    private int mAmount;

    public String getPayerAccountName() {
        return mPayerAccountName;
    }

    public void setPayerAccountName(String payerAccountName) {
        mPayerAccountName = payerAccountName;
    }

    public String getReceiverAccountName() {
        return mReceiverAccountName;
    }

    public void setReceiverAccountName(String receiverAccountName) {
        mReceiverAccountName = receiverAccountName;
    }

    public String getPayerWalletName() {
        return mPayerWalletName;
    }

    public void setPayerWalletName(String payerWalletName) {
        mPayerWalletName = payerWalletName;
    }

    public String getReceiverWalletName() {
        return mReceiverWalletName;
    }

    public void setReceiverWalletName(String receiverWalletName) {
        mReceiverWalletName = receiverWalletName;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public Transaction() {
    }

    public Transaction(String payerWalletName, String receiverWalletName, String payerAccountName, String receiverAccountName, int amount) {
        mPayerWalletName = payerWalletName;
        mReceiverWalletName = receiverWalletName;
        mPayerAccountName = payerAccountName;
        mReceiverAccountName = receiverAccountName;
        mAmount = amount;
    }
}
