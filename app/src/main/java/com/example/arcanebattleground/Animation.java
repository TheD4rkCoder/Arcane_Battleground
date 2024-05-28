package com.example.arcanebattleground;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Animation {
    public static final int animationLength = 10;
    public static int currentAnimationCounter = animationLength;
    public static float startX;
    public static float startY;
    public static float endX;
    public static float endY;
    public static Bitmap[] animationSprites;
    public static List<GameEntity> entitiesToHide;
    private static boolean animationJustEnded = false;
    public static boolean stayAfterEnd;

    public static boolean isAnimationPlaying() {
        boolean isPlaying = currentAnimationCounter < animationLength;
        if (isPlaying) {
            animationJustEnded = true;
            return true;
        }
        if (animationJustEnded) {
            for (GameEntity e : entitiesToHide) {
                e.setVisibility(true);
            }
        }
        animationJustEnded = false;
        return false;
    }

    public static float getCurrentX() {
        return startX + (endX - startX) * currentAnimationCounter / animationLength;
    }

    public static float getCurrentY() {
        return startY + (endY - startY) * currentAnimationCounter / animationLength;
    }

    public static Bitmap getNextAnimationBitmap() {
        return animationSprites[currentAnimationCounter++ % animationSprites.length];
    }
}
