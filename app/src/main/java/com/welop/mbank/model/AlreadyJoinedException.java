package com.welop.mbank.model;

/**
 * Exception which is thrown when try to add player to a party he'd already joined.
 */
public class AlreadyJoinedException extends Throwable {
    private Account account;
    private Lobby lobby;

    /**
     * Returns an account caused exception.
     * @return An account which is already member of the lobby.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * The lobby that already contains reference to the account.
     * @return Lobby.
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Exception constructor.
     * @param account The account which is already member of the lobby.
     * @param lobby The lobby with the joined account.
     */
    public AlreadyJoinedException(Account account, Lobby lobby) {
        this.account = account;
        this.lobby = lobby;
    }
}
