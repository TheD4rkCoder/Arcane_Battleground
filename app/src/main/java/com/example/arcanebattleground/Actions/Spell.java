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
    private String name;

    public Spell(String name, int manaCost, int radius, Bitmap icon) {
        this.icon = Bitmap.createScaledBitmap(icon, 16, 16, false);
        this.icon = Bitmap.createScaledBitmap(this.icon, (int)(screenWidth*0.225), (int)(screenWidth*0.225), false);
        this.name = name;
        this.manaCost = manaCost;
        this.radius = radius;
    }

    @Override
    public boolean descriptionTap(float x, GameEntity e) {
        GameView.resetToDefaultAction();
        return false;
    }

    @Override
    public void drawDescription(Canvas c, int startY, int height, GameEntity e) {
        c.drawBitmap(GameView.spellSlotBitmap, 0.02f * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
        c.drawBitmap(icon, 0.02f * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
        c.drawRect(0.755f * screenWidth, startY + 0.02f * screenWidth, 0.98f * screenWidth, screenHeight - 0.02f * screenWidth, GameView.paintForShapes);
        c.drawBitmap(GameView.cancelBitmap, 0.755f * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
    }
}
