package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getDistance;

import android.graphics.Bitmap;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.GameView;
import com.example.arcanebattleground.Player;

public class SummonSpell extends Spell {
    private int maxHealth;
    private Bitmap[] idleAnimation;

    public SummonSpell(String name, int manaCost, int radius, Bitmap icon, int maxHealth, Bitmap[] idleAnimation) {
        super(name, manaCost, radius, icon);
        this.maxHealth = maxHealth;
        this.idleAnimation = idleAnimation;
    }

    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        Player p = e.getLinkedPlayer();
        if (p.getMana() < manaCost || getDistance(x, e.getX(), y, e.getY()) > radius)
            return false;

        GameEntity skeleton = new GameEntity(idleAnimation, maxHealth, new DefaultSkeletonAction(), p);
        skeleton.setX(x);
        skeleton.setY(y);
        GameView.addEntityToList(skeleton);
        p.setMana(p.getMana() - manaCost);
        GameView.startAnimation(p.getX(), p.getY(), x, y, animationSprites, false);
        p.setRoundsCast(0);
        return true;
    }
}
