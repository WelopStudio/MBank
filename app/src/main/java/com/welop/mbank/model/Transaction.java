package com.welop.mbank.model;

import com.google.firebase.Timestamp;

import java.util.Comparator;

public class Transaction {
    public static Comparator<Transaction> DateComparator = new Comparator<Transaction>() {
        @Override
        public int compare(Transaction o1, Transaction o2) {
            return -o1.mCreatedAt.compareTo(o2.mCreatedAt);
        }
    };

    private String mPayerWalletName;
    private String mReceiverWalletName;
    private String mPayerAccountName;
    private String mReceiverAccountName;
    private Long mAmount;
    private Timestamp mCreatedAt;

    public Timestamp getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        mCreatedAt = createdAt;
    }

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

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(Long amount) {
        mAmount = amount;
    }

    public Transaction() {
    }
}
