package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getCollidingEntities;
import static com.example.arcanebattleground.GameView.getDistance;
import static com.example.arcanebattleground.GameView.screenHeight;
import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;

import java.util.ArrayList;

public class Tier1FireSparks extends Spell {
    int damage = 9;

    public Tier1FireSparks() {
        radius = 2;
        manaCost = 6;
        icon = Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 0, 0, 32, 32), (int)(screenWidth*0.225), (int)(screenWidth*0.225), false);
        // animationSprites =
    }

    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        if (getDistance(x, e.getX(), y, e.getY()) <= radius) {
            ArrayList<GameEntity> entitiesToHit = getCollidingEntities(x, y);
            for (GameEntity en : entitiesToHit) {
                en.setHealth(en.getHealth() - damage);
            }
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

    }
}
