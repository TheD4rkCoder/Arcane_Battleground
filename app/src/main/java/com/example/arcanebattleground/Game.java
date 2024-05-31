package com.example.arcanebattleground;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Game {
    private int gameId;
    private ArrayList<Entity> gameEntities;
    private ArrayList<PlayerInfo> players;
    private boolean started = false;
    private Entity tapToPublish = null;
    public Game(int gameId, PlayerInfo host){
        this.gameId = gameId;
        players = new ArrayList<>();
        players.add(host);
        gameEntities = new ArrayList<>();
    }

    public ArrayList<Entity> getGameEntities() {
        return gameEntities;
    }

    public void setGameEntities(ArrayList<Entity> gameEntities) {
        this.gameEntities = gameEntities;
    }

    public ArrayList<PlayerInfo> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerInfo> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Game)obj).gameId == this.gameId;
    }

}

