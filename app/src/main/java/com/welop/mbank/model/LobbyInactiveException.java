package com.welop.mbank.model;

public class LobbyInactiveException extends Throwable {
    private Lobby lobby;

    public Lobby getLobby() {
        return lobby;
    }

    public LobbyInactiveException(Lobby lobby) {
        this.lobby = lobby;
    }
}
