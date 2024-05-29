package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getDistance;
import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;
import com.example.arcanebattleground.R;

import java.util.Collections;

public class Tier1Sprint extends Spell{
    public Tier1Sprint() {
        super("Sprint", 20, 3, GameView.tier1SprintSpellBitmap);
        // animationSprites =
    }
    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        int dist = getDistance(e.getX(), x,e.getY(), y);
        if (dist != 0 && dist <= radius) {
            GameView.startAnimation(e.getX(), e.getY(), x, y, e.getAnimationSprites(), false, Collections.singletonList(e));
            e.setX(x);
            e.setY(y);
            return true;
        }
        return false;
    }
}
