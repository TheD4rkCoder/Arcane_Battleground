package com.example.arcanebattleground.Actions;
import static com.example.arcanebattleground.GameView.screenWidth;

import android.graphics.Bitmap;
import com.example.arcanebattleground.GameView;

public class Tier1FireSparks extends SimpleAttackSpell {
    public Tier1FireSparks() {
        super("Fire Sparks", 6, 3, 9, Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 0, 0, 32, 32), (int)(screenWidth*0.225), (int)(screenWidth*0.225), false));
    }
}
