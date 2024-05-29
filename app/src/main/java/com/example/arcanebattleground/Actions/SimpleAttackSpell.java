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
import com.example.arcanebattleground.Player;

import java.util.ArrayList;

public class SimpleAttackSpell extends Spell {
    int damage;

    public SimpleAttackSpell(String name, int manaCost, int radius, int damage, Bitmap icon) {
        super(name, manaCost, radius,icon);
        this.damage = damage;
        // animationSprites =
    }

    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        Player p = e.getLinkedPlayer();
        if (p.getMana() < manaCost)
            return false;
        if (getDistance(x, e.getX(), y, e.getY()) <= radius) {
            ArrayList<GameEntity> entitiesToHit = getCollidingEntities(x, y);
            for (GameEntity en : entitiesToHit) {
                en.setHealth(en.getHealth() - damage);
            }
            p.setMana(p.getMana() - manaCost);
            GameView.startAnimation(p.getX(), p.getY(), x, y, new Bitmap[]{icon}, false);
            return true;
        }
        return false;
    }
}
