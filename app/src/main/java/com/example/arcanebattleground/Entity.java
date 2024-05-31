package com.example.arcanebattleground;

import androidx.annotation.Nullable;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        assert obj != null;
        return ((Entity)obj).id.equals(this.id);
    }
}
