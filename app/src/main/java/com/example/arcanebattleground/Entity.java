package com.example.arcanebattleground;

public class Entity {
    private int x;
    private int y;
    private String id;

    public Entity(int x, int y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
