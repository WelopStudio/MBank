package com.welop.mbank.model;

/**
 * Exception which is thrown when try to transfer more money than payer have.
 */
public class WithdrawException extends Exception {
    private Wallet wallet;
    private int lack;

    /**
     * Returns the wallet of payer.
     * @return The wallet of payer.
     */
    public Wallet getWallet() {
        return wallet;
    }

    /**
     * Returns the lack of funds to make the transaction successful.
     * @return The lack of funds to make the transaction successful.
     */
    public int getLack() {
        return lack;
    }

    /**
     * Exception constructor.
     * @param wallet The wallet of payer.
     * @param lack The lack of funds to make the transaction successful.
     */
    public WithdrawException(Wallet wallet, int lack) {
        this.wallet = wallet;
        this.lack = lack;
    }

    @Override
    public String toString() {
        return wallet + " - transaction failed, $" + lack + " lack.";
    }
}
