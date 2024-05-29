package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.getDistance;

import android.graphics.Bitmap;

import com.example.arcanebattleground.GameEntity;
import com.example.arcanebattleground.Player;

public class PlayerRecoverySpell extends Spell {
    private int increaseHealthAmount;
    private int increaseMaxHealthAmount;
    private int increaseManaAmount;
    private int increaseMaxManaAmount;

    public PlayerRecoverySpell(String name, int manaCost, int increaseHealthAmount, int increaseMaxHealthAmount, int increaseManaAmount, int increaseMaxManaAmount, Bitmap icon) {
        super(name, manaCost, 0, icon);
        this.increaseHealthAmount = increaseHealthAmount;
        this.increaseMaxHealthAmount = increaseMaxHealthAmount;
        this.increaseManaAmount = increaseManaAmount;
        this.increaseMaxManaAmount = increaseMaxManaAmount;
    }

    @Override
    public boolean boardTap(int x, int y, GameEntity e) {
        Player p = (Player) e;
        if (getDistance(p.getX(), x, p.getY(), y) == 0 && p.getMana() >= manaCost) {
            p.setMaxMana(p.getMaxMana() + increaseMaxManaAmount);
            if (p.getMaxMana() > p.getStatCaps())
                p.setMaxHealth(p.getStatCaps());
            p.setMaxHealth(p.getMaxHealth() + increaseMaxHealthAmount);
            if (p.getMaxMana() > p.getStatCaps())
                p.setMaxMana(p.getStatCaps());
            p.setMana(p.getMana() + increaseManaAmount - manaCost);
            if (p.getMana() > p.getMaxMana())
                p.setMana(p.getMaxMana());
            p.setHealth(p.getHealth() + increaseHealthAmount);
            if (p.getHealth() > p.getMaxHealth())
                p.setHealth(p.getMaxHealth());

            p.setRoundsCast(0);
            // still need to start animation
            return true;
        }
        return false;
    }
}
