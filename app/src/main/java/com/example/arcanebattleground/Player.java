package com.example.arcanebattleground;

import android.graphics.Bitmap;

public class Player {
    private final int statCaps = 999;
    private int[] position = {0, 0};
    private Bitmap[] movementAnimationSprites;
    private Bitmap idleSprite; // could later be extended

    private int mana = 100;
    private int maxMana = 100;
    private int health = 100;
    private int maxHealth = 100;
    private int roundsCast = 0;

    public Player (Bitmap idleSprite) {
        this.idleSprite = idleSprite;
    }

    public Bitmap getIdleSprite() {
        return idleSprite;
    }

    public int getX() {
        return position [0];
    }

    public void setX(int x) {
        this.position[0] = x;
    }

    public int getY() {
        return position[1];
    }

    public void setY(int y) {
        this.position[1] = y;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getRoundsCast() {
        return roundsCast;
    }

    public void setRoundsCast(int roundsCast) {
        this.roundsCast = roundsCast;
    }
}
