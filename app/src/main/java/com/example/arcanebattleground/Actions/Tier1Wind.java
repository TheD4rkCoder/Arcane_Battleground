package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getCollidingEntities;
import static com.example.arcanebattleground.GameView.getDistance;
import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;
import com.example.arcanebattleground.Player;
import com.example.arcanebattleground.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tier1Wind extends Spell{
    public Tier1Wind() {
        super("Winds", 30, 2, GameView.windIconBitmap);
    }
    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        Player p = e.getLinkedPlayer();
        if (p.getMana() < manaCost)
            return false;
        if (getDistance(x, e.getX(), y, e.getY()) <= radius) {
            GameView.currentAction = new Tier1Wind2(x, y, 2, manaCost);

            GameView.startAnimation(p.getX(), p.getY(), x, y, animationSprites, true);
        }
        return false;
    }
}
