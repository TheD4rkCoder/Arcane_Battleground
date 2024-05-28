package com.example.arcanebattleground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.arcanebattleground.Actions.Action;
import com.example.arcanebattleground.Actions.DefaultPlayerAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player extends GameEntity {
    public static Bitmap castIndicatorBitmap;
    public static Bitmap castButtonBitmap;
    public static Bitmap castAnimation[];
    private final int statCaps = 999;
    private static Action defaultAction;
    private int mana = 50;
    private int maxMana = 100;
    private int roundsCast = 0;

    public Player(Bitmap[] sprites) {
        super(sprites);
        setMaxHealth(100);
        setHealth(100);
        setLinkedPlayer(this);
    }

    @Override
    public void drawOnBoard(Canvas c, float hexagonCenterX, float hexagonCenterY) {
        super.drawOnBoard(c, hexagonCenterX, hexagonCenterY);
        for (int j = 0; j < roundsCast; j++) {
            c.drawBitmap(castIndicatorBitmap, hexagonCenterX - (((roundsCast - 2 * j) * castIndicatorBitmap.getWidth()) >> 1), hexagonCenterY - GameView.hexagonHeight * 0.55f - (castIndicatorBitmap.getHeight() >> 1), GameView.paintForBitmaps);
        }
    }

    @Override
    public void drawEntityDescription(Canvas c, float startY, float height) {
        GameView.paintForTexts.setColor(Color.RED);
        c.drawText("" + health + "/" + maxHealth, GameView.screenWidth * 0.35f, startY + GameView.paintForTexts.getTextSize(), GameView.paintForTexts);
        GameView.paintForTexts.setColor(Color.BLUE);

        c.drawText("" + mana + "/" + maxMana, GameView.screenWidth * 0.97f, startY + GameView.paintForTexts.getTextSize(), GameView.paintForTexts);

        if (mana < 10)
            GameView.paintForBitmaps.setAlpha(50);
        c.drawBitmap(castButtonBitmap, GameView.screenWidth * 0.5f - (castButtonBitmap.getWidth() >> 1), startY + 20, GameView.paintForBitmaps);
        GameView.paintForBitmaps.setAlpha(255);
    }

    @Override
    public boolean tapEntityDescription() {
        if (mana < 10)
            return false;
        mana -= 10;
        roundsCast++;
        // start animation
        GameView.startAnimation(getX(), getY(), getX(), getY(), castAnimation, false);
        return true;
    }

    @Override
    public Action getDefaultAction() {
        return defaultAction;
    }

    @Override
    public void setDefaultAction(Action action) {
        defaultAction = action;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }


    public int getRoundsCast() {
        return roundsCast;
    }

    public void setRoundsCast(int roundsCast) {
        this.roundsCast = roundsCast;
    }
}
