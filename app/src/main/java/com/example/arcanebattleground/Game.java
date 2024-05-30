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



    @Override
    public boolean equals(Object obj) {
        return ((Game)obj).gameId == this.gameId;
    }
}

