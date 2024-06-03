package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getDistance;
import static com.example.arcanebattleground.GameView.screenHeight;
import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;
import com.example.arcanebattleground.Player;
import com.example.arcanebattleground.R;

import java.util.ArrayList;
import java.util.Collections;

public class DefaultPlayerAction extends Action {

    private ArrayList<Spell[]> spells = new ArrayList<>();
    private Bitmap[] meditationAnimation;

    public DefaultPlayerAction() {
        radius = 1;
        Spell[] tier1Spells = new Spell[4];
        tier1Spells[0] = new Tier1FireSparks();
        tier1Spells[1] = new Tier1Wind();
        tier1Spells[2] = new Tier1Sprint();
        spells.add(tier1Spells);
        Spell[] tier2Spells = new Spell[4];
        tier2Spells[0] = new Tier2Fireball();
        tier2Spells[1] = new Tier2WindBlade();
        tier2Spells[2] = new Tier2RockThrow();
        tier2Spells[3] = new Tier2Heal();
        spells.add(tier2Spells);

        Spell[] tier3Spells = new Spell[4];
        tier3Spells[0] = new Tier3SummonSkeleton();
        spells.add(tier3Spells);

        for(int i = 3; i < 6; i++) {
            spells.add(new Spell[4]);
        }

        Spell[] tier7Spells = new Spell[4];
        tier7Spells[0] = new Tier10GeneOptimization();
        spells.add(tier7Spells);



        meditationAnimation = new Bitmap[5];
        meditationAnimation[0] = GameView.meditationAnimationBitmap;
        meditationAnimation[1] = Bitmap.createScaledBitmap(GameView.meditationAnimationBitmap, (int)(1.7*GameView.hexagonWidth), (int)(1.7*GameView.hexagonHeight), false);
        meditationAnimation[2] = Bitmap.createScaledBitmap(GameView.meditationAnimationBitmap, (int)(1.4*GameView.hexagonWidth), (int)(1.4*GameView.hexagonHeight), false);
        meditationAnimation[3] = Bitmap.createScaledBitmap(GameView.meditationAnimationBitmap, (int)(1.1*GameView.hexagonWidth), (int)(1.1*GameView.hexagonHeight), false);
        meditationAnimation[4] = Bitmap.createScaledBitmap(GameView.meditationAnimationBitmap, (int)(0.9*GameView.hexagonWidth), (int)(0.9*GameView.hexagonHeight), false);

    }

    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        Player p = (Player) e;
        if (getDistance(p.getX(), x, p.getY(), y) == 0) {
            int mana = p.getMana();
            mana += p.getMaxMana()/5;
            if (mana > p.getMaxMana())
                mana = p.getMaxMana();
            p.setMana(mana);
            p.setRoundsCast(0);
            // still need to start meditationAnimation
            GameView.startAnimation(p.getX(), p.getY(), x, y, meditationAnimation, false);
            return true;
        }
        if (getDistance(p.getX(), x, p.getY(), y) <= radius) {
            GameView.startAnimation(p.getX(), p.getY(), x, y, p.getAnimationSprites(), false, Collections.singletonList(p));
            p.setX(x);
            p.setY(y);
            p.setRoundsCast(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean descriptionTap(float x, GameEntity e) {
        Player p = (Player) e;
        if (p.getRoundsCast() >= spells.size()) {
            return false;
        }

        if (x < 0.25f * screenWidth) {
            if (spells.get(p.getRoundsCast())[0] != null && spells.get(p.getRoundsCast())[0].manaCost <= p.getMana())
                GameView.currentAction = spells.get(p.getRoundsCast())[0];
        } else if (x < 0.5f * screenWidth) {
            if (spells.get(p.getRoundsCast())[1] != null && spells.get(p.getRoundsCast())[1].manaCost <= p.getMana())
                GameView.currentAction = spells.get(p.getRoundsCast())[1];
        } else if (x < 0.75f * screenWidth) {
            if (spells.get(p.getRoundsCast())[2] != null && spells.get(p.getRoundsCast())[2].manaCost <= p.getMana())
                GameView.currentAction = spells.get(p.getRoundsCast())[2];
        } else {
            if (spells.get(p.getRoundsCast())[3] != null && spells.get(p.getRoundsCast())[3].manaCost <= p.getMana())
                GameView.currentAction = spells.get(p.getRoundsCast())[3];
        }
        return false;
    }

    @Override
    public void drawDescription(Canvas c, int startY, int height, GameEntity e) {
        Player p = (Player) e;
        if (p.getRoundsCast() >= spells.size())
            return;

        Spell[] spellsOfCurrentTier = spells.get(p.getRoundsCast());
        for (int i = 0; i < spellsOfCurrentTier.length; i++) {
            if (spellsOfCurrentTier[i] == null || spellsOfCurrentTier[i].icon == null)
                continue;
            if (spellsOfCurrentTier[i].manaCost > p.getMana())
                GameView.paintForBitmaps.setAlpha(100);
            c.drawBitmap(GameView.spellSlotBitmap, (0.02f + 0.245f * i) * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
            c.drawBitmap(spellsOfCurrentTier[i].icon, (0.02f + 0.245f * i) * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
            GameView.paintForBitmaps.setAlpha(255);

            GameView.paintForTexts.setTextAlign(Paint.Align.RIGHT);
            GameView.paintForTexts.setColor(Color.parseColor("#30a2ff"));
            c.drawText("" + spellsOfCurrentTier[i].manaCost, (0.245f + 0.245f * i) * screenWidth, screenHeight - screenWidth*0.023f, GameView.paintForTexts);
        }
    }
}
