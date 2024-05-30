package com.example.arcanebattleground;


import java.net.Socket;

public class PlayerInfo {
    private Socket playerSocket;
    private int id;
    private String name;

    public PlayerInfo(Socket playerSocket, int id, String name) {
        this.playerSocket = playerSocket;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

}

