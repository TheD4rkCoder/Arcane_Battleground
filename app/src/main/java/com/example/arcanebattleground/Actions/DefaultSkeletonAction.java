package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getDistance;
import static com.example.arcanebattleground.GameView.screenHeight;
import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;
import com.example.arcanebattleground.Player;

import java.util.ArrayList;
import java.util.Collections;

import javax.net.ssl.SSLPeerUnverifiedException;

public class DefaultSkeletonAction extends Action {
    private Spell[] spells = new Spell[4];

    public DefaultSkeletonAction() {
        radius = 2;
        spells[0] = new Tier2RockThrow();
        spells[1] = new Tier1Sprint();
    }

    @Override
    public boolean boardTap(int x, int y, GameEntity e) {

        if (getDistance(e.getX(), x, e.getY(), y) != 0 && getDistance(e.getX(), x, e.getY(), y) <= radius) {
            GameView.startAnimation(e.getX(), e.getY(), x, y, e.getAnimationSprites(), false, Collections.singletonList(e));
            e.setX(x);
            e.setY(y);
            return true;
        }
        return false;
    }

    @Override
    public boolean descriptionTap(float x, GameEntity e) {
        Player p = e.getLinkedPlayer();
        if (x < 0.25f * screenWidth) {
            if (spells[0] != null && spells[0].manaCost <= p.getMana())
                GameView.currentAction = spells[0];
        } else if (x < 0.5f * screenWidth) {
            if (spells[1] != null && spells[1].manaCost <= p.getMana())
                GameView.currentAction = spells[1];
        } else if (x < 0.75f * screenWidth) {
            if (spells[2] != null && spells[2].manaCost <= p.getMana())
                GameView.currentAction = spells[2];
        } else {
            if (spells[3] != null && spells[3].manaCost <= p.getMana())
                GameView.currentAction = spells[3];
        }
        return false;
    }

    @Override
    public void drawDescription(Canvas c, int startY, int height, GameEntity e) {
        Player p =  e.getLinkedPlayer();

        for (int i = 0; i < spells.length; i++) {
            if (spells[i] == null || spells[i].icon == null)
                continue;
            if (spells[i].manaCost > p.getMana())
                GameView.paintForBitmaps.setAlpha(100);
            c.drawBitmap(GameView.spellSlotBitmap, (0.02f + 0.245f * i) * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
            c.drawBitmap(spells[i].icon, (0.02f + 0.245f * i) * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
            GameView.paintForBitmaps.setAlpha(255);

            GameView.paintForTexts.setTextAlign(Paint.Align.RIGHT);
            GameView.paintForTexts.setColor(Color.parseColor("#30a2ff"));
            c.drawText("" + spells[i].manaCost, (0.245f + 0.245f * i) * screenWidth, screenHeight - screenWidth * 0.023f, GameView.paintForTexts);
        }
    }
}
