package com.example.arcanebattleground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.arcanebattleground.Actions.Action;
import com.example.arcanebattleground.Actions.DefaultPlayerAction;
import com.example.arcanebattleground.Actions.DefaultSkeletonAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shared.PlayerInfo;

public class Player extends GameEntity {
    public static Bitmap castIndicatorBitmap;
    public static Bitmap castButtonBitmap;
    public static Bitmap castAnimation[];
    private final int statCaps = 999;
    private int mana = 50;
    private int maxMana = 100;
    private final int maxRoundsCast = 6;
    private int roundsCast = 0;
    private PlayerInfo playerInfo;

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public Player(Bitmap[] sprites, PlayerInfo playerInfo) {
        super(sprites, 100, new DefaultPlayerAction());
        setLinkedPlayer(this);
        this.playerInfo = playerInfo;
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
        GameView.paintForTexts.setTextAlign(Paint.Align.RIGHT);
        GameView.paintForTexts.setColor(Color.RED);
        c.drawText("" + health + "/" + maxHealth, GameView.screenWidth * 0.35f, startY + GameView.paintForTexts.getTextSize() + GameView.screenWidth * 0.02f, GameView.paintForTexts);
        GameView.paintForTexts.setColor(Color.parseColor("#30a2ff"));
        c.drawText("" + mana + "/" + maxMana, GameView.screenWidth * 0.97f, startY + GameView.paintForTexts.getTextSize() + GameView.screenWidth * 0.02f, GameView.paintForTexts);

        if (mana < 10 || roundsCast >= maxRoundsCast)
            GameView.paintForBitmaps.setAlpha(50);
        c.drawBitmap(castButtonBitmap, GameView.screenWidth * 0.5f - (castButtonBitmap.getWidth() >> 1), startY + 20, GameView.paintForBitmaps);
        GameView.paintForBitmaps.setAlpha(255);
    }

    @Override
    public boolean tapEntityDescription() {
        if (roundsCast >= maxRoundsCast)
            return false;
        if (mana < 10)
            return false;
        mana -= 10;
        roundsCast++;
        // start animation
        GameView.startAnimation(getX(), getY(), getX(), getY(), castAnimation, false);
        return true;
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
    public int getStatCaps() {
        return statCaps;
    }
}
