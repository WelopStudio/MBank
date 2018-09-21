package com.welop.mbank.model;

/**
 * Transaction Manager is a Lobby component that provides account instances usage (instead of wallets) to transfer money inside lobby.
 */
class TransactionManager {
    private Lobby lobby;

    /**
     * Package-private constructor. Called by Lobby constructor.
     * @param lobby Connected lobby.
     */
    TransactionManager(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Transfers money from one Wallet to another using Account instances.
     * @param from Payer Account instance.
     * @param to Receiver Account instance.
     * @param amount Amount of money to be transferred.
     */
    void transfer(Account from, Account to, int amount) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
            if (!lobby.getActive())
                throw new LobbyInactiveException(lobby);

            lobby.getWallets().get(from).transfer(lobby.getWallets().get(to), amount);
    }

    /**
     * Withdraws money from Wallet by Account.
     * @param from Payer Account instance.
     * @param amount Amount of money.
     */
    void withdraw(Account from, int amount) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
            if (!lobby.getActive())
                throw new LobbyInactiveException(lobby);

            lobby.getWallets().get(from).withdraw(amount);
    }

    /**
     * Deposits money to Wallet by Account
     * @param to Receiver Account instance.
     * @param amount Amount of money.
     */
    void deposit(Account to, int amount) throws LobbyInactiveException, NonpositiveAmountException {
            if (!lobby.getActive())
                throw new LobbyInactiveException(lobby);

            lobby.getWallets().get(to).deposit(amount);
    }

    /**
     * Deposits "GO" amount of money to the Wallet by Account.
     * @param to Receiver Account instance.
     */
    void go(Account to) throws LobbyInactiveException, NonpositiveAmountException {
        deposit(to, lobby.getGameSettings().getGoCost());
    }

    /**
     * Withdraws LUXURY TAX amount of money from the Wallet by Account.
     * @param from Payer Account instance.
     */
    void luxuryTax(Account from) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
        withdraw(from, lobby.getGameSettings().getLuxuryTaxCost());
    }

    /**
     * Withdraws INCOME TAX amount of money from the Wallet by Account.
     * @param from Payer Account instance.
     */
    void incomeTax(Account from) throws LobbyInactiveException, WithdrawException, NonpositiveAmountException {
        withdraw(from, lobby.getGameSettings().getIncomeTaxCost());
    }

    /**
     * Makes transactions of an amount of money from one wallet to all other.
     * @param from Payer Account instance.
     * @param amount An amount of money to be received by each other wallet.
     */
    void payEach(Account from, int amount) throws WithdrawException, LobbyInactiveException, NonpositiveAmountException {
            Wallet walletFrom = lobby.getWallets().get(from);

            if (walletFrom.getBalance() < amount * (lobby.getWallets().size() - 1))
                throw new WithdrawException(walletFrom, amount * (lobby.getWallets().size() - 1) - walletFrom.getBalance());

            for (Wallet to : lobby.getWallets().values()) {
                if (walletFrom != to)
                    transfer(walletFrom.getOwner(), to.getOwner(), amount);
            }
    }

    /**
     * Makes transactions of an amount of money from all wallets to specified one.
     * @param to Receiver Account instance.
     * @param amount An amount of money to be received by specified wallet.
     */
    void collectFromEveryone(Account to, int amount) throws WithdrawException, LobbyInactiveException, NonpositiveAmountException {
            Wallet walletTo = lobby.getWallets().get(to);
            for (Wallet w : lobby.getWallets().values())
                if (walletTo != w)
                    if (w.getBalance() < amount)
                        throw new WithdrawException(w, amount - w.getBalance());
            for (Wallet w : lobby.getWallets().values())
                if (walletTo != w)
                    transfer(w.getOwner(), walletTo.getOwner(), amount);
    }
}
