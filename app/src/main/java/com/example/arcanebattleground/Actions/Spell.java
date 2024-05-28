package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.screenHeight;
import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;

public abstract class Spell extends Action {
    public int manaCost;
    public Bitmap icon;
    public Bitmap[] animationSprites;
    String name;
    @Override
    public void drawDescription(Canvas c, int startY, int height, GameEntity e) {
        GameView.paintForShapes.setColor(Color.GRAY);
        c.drawRect(0.02f * screenWidth, startY + 0.02f * screenWidth, 0.245f * screenWidth, screenHeight - 0.02f * screenWidth, GameView.paintForShapes);
        c.drawBitmap(icon, 0.02f * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
        c.drawRect(0.755f * screenWidth, startY + 0.02f * screenWidth, 0.98f * screenWidth, screenHeight - 0.02f * screenWidth, GameView.paintForShapes);
        c.drawBitmap(GameView.cancelBitmap, 0.755f * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
    }
}
