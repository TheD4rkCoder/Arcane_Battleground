package com.example.arcanebattleground.Actions;

import android.graphics.Bitmap;

import com.example.arcanebattleground.GameView;

public abstract class Spell extends Action {
    protected int manaCost;
    protected Bitmap icon;
    protected Bitmap[] animationSprites;

}
