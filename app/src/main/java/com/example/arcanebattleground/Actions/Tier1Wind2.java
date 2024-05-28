package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getCollidingEntities;
import static com.example.arcanebattleground.GameView.getDistance;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;
import com.example.arcanebattleground.Player;

import java.util.ArrayList;

public class Tier1Wind2 extends Spell {
    private int fromX, fromY;
    public Tier1Wind2(int x, int y, int radius, int cost) {
        this.radius = radius;
        this.manaCost = cost;
        this.icon = GameView.windIconBitmap;
        this.name = "Winds";
        this.fromX = x;
        this.fromY = y;
        // animationSprites =
    }
    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        Player p = e.getLinkedPlayer();
        if (getDistance(x, e.getX(), y, e.getY()) <= radius) {
            ArrayList<GameEntity> entitiesToPush = getCollidingEntities(fromX, fromY);
            GameView.startAnimation(fromX, fromY, x, y, new Bitmap[]{icon}, false, entitiesToPush);
            for (GameEntity en : entitiesToPush) {
                en.setX(x);
                en.setY(y);
            }
            p.setMana(p.getMana() - manaCost);
            return true;
        }
        return false;
    }

    @Override
    public boolean descriptionTap(float x, GameEntity e) {
        GameView.resetToDefaultAction();
        return false;
    }

    @Override
    public void drawDescription(Canvas c, int startY, int height, GameEntity e) {
        super.drawDescription(c, startY, height, e);
    }
}
