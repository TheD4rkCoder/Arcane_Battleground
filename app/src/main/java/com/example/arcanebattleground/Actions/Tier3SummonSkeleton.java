package com.example.arcanebattleground.Actions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.arcanebattleground.GameView;
import com.example.arcanebattleground.R;

public class Tier3SummonSkeleton extends SummonSpell{
    public Tier3SummonSkeleton() {
        super("Summon Skeleton", 30, 3, GameView.skullBitmap, 30, new Bitmap[] {
                Bitmap.createScaledBitmap(Bitmap.createBitmap(Bitmap.createScaledBitmap(GameView.skeletonSkullSpriteSheet, 121, 16, false), 0, 0, 16, 16), (int) (0.7 * GameView.hexagonWidth), (int) (0.7 * GameView.hexagonHeight), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(Bitmap.createScaledBitmap(GameView.skeletonSkullSpriteSheet, 121, 16, false), 16, 0, 16, 16), (int) (0.7 * GameView.hexagonWidth), (int) (0.7 * GameView.hexagonHeight), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(Bitmap.createScaledBitmap(GameView.skeletonSkullSpriteSheet, 121, 16, false), 32, 0, 16, 16), (int) (0.7 * GameView.hexagonWidth), (int) (0.7 * GameView.hexagonHeight), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(Bitmap.createScaledBitmap(GameView.skeletonSkullSpriteSheet, 121, 16, false), 48, 0, 16, 16), (int) (0.7 * GameView.hexagonWidth), (int) (0.7 * GameView.hexagonHeight), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(Bitmap.createScaledBitmap(GameView.skeletonSkullSpriteSheet, 121, 16, false), 64, 0, 16, 16), (int) (0.7 * GameView.hexagonWidth), (int) (0.7 * GameView.hexagonHeight), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(Bitmap.createScaledBitmap(GameView.skeletonSkullSpriteSheet, 121, 16, false), 80, 0, 16, 16), (int) (0.7 * GameView.hexagonWidth), (int) (0.7 * GameView.hexagonHeight), false)


        });
    }
}
