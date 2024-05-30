package com.example.arcanebattleground.Actions;

import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Bitmap;

import com.example.arcanebattleground.GameView;

public class Tier2Fireball extends SimpleAttackSpell{
    public Tier2Fireball() {
        super("Fireball", 10, 4, 20, GameView.lavapoolBitmap);
    }
}
