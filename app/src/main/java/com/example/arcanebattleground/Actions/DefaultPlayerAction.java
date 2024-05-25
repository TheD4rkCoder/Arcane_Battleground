package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getDistance;
import static com.example.arcanebattleground.GameView.screenHeight;
import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Canvas;
import android.graphics.Color;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;
import com.example.arcanebattleground.Player;

import java.util.ArrayList;

public class DefaultPlayerAction extends Action{

    private ArrayList<Spell[]> spells = new ArrayList<>();
    public DefaultPlayerAction() {
        radius = 1;
        Spell[] tier1Spells = new Spell[4];
        tier1Spells[0] = new Tier1FireSparks();
        spells.add(tier1Spells);
    }
    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        Player p = (Player) e;
        if (getDistance(p.getX(), x, p.getY(), y) == 0) {
            int mana = p.getMana();
            mana += 20;
            if (mana > p.getMaxMana())
                mana = p.getMaxMana();
            p.setMana(mana);
            return true;
        }
        if (getDistance(p.getX(), x, p.getY(), y) <= radius) {
            p.setX(x);
            p.setY(y);
            return true;
        }
        return false;
    }

    @Override
    public boolean descriptionTap(float x, GameEntity e) {
        if (x < 0.25f * screenWidth) {
        } else if (x < 0.5f * screenWidth) {
        } else if (x < 0.75f * screenWidth) {
        } else {
        }
        return false;
    }

    @Override
    public void drawDescription(Canvas c, int startY, int height, GameEntity e) {
        GameView.paintForShapes.setColor(Color.GRAY);
        c.drawRect(0.02f * screenWidth, startY + 0.02f * screenWidth, 0.245f * screenWidth, screenHeight - 0.02f * screenWidth, GameView.paintForShapes);
        c.drawRect(0.265f * screenWidth, startY + 0.02f * screenWidth, 0.49f * screenWidth, screenHeight - 0.02f * screenWidth, GameView.paintForShapes);
        c.drawRect(0.51f * screenWidth, startY + 0.02f * screenWidth, 0.735f * screenWidth, screenHeight - 0.02f * screenWidth, GameView.paintForShapes);
        c.drawRect(0.755f * screenWidth, startY + 0.02f * screenWidth, 0.98f * screenWidth, screenHeight - 0.02f * screenWidth, GameView.paintForShapes);
        Player p = (Player) e;
        if (p.getRoundsCast() < spells.size()) {
            Spell[] spellsOfCurrentTier = spells.get(p.getRoundsCast());
            for (int i = 0; i < spellsOfCurrentTier.length; i++) {
                if (spellsOfCurrentTier[i] == null)
                    continue;
                c.drawBitmap(spellsOfCurrentTier[i].icon, (0.02f + 22.5f * i) * screenWidth, startY + 0.02f * screenWidth, GameView.paintForBitmaps);
            }
        }
    }
}
