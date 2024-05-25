package com.example.arcanebattleground.Actions;

import android.graphics.Canvas;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.Player;

public abstract class Action {
    public int radius;
    public abstract boolean boardTap(int x, int y, GameEntity e);
    public abstract boolean descriptionTap(float x, GameEntity e);
    public abstract void drawDescription(Canvas c, int startY, int height, GameEntity e);

}
