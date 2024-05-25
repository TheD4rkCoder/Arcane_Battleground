package com.example.arcanebattleground;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.arcanebattleground.Actions.Action;

public abstract class GameEntity {
    private int[] position = {0, 0};
    private Bitmap[] animationSprites;
    private int currentSprite = 0;
    protected int health;
    protected int maxHealth;
    private Player linkedPlayer;
    public GameEntity(Bitmap[] animationSprites) {
        this.animationSprites = animationSprites;
    }
    public void drawOnBoard(Canvas c, float hexagonCenterX, float hexagonCenterY) {
        Bitmap b = this.getNextSprite();
        c.drawBitmap(b, hexagonCenterX- (b.getWidth() >> 1), hexagonCenterY - (b.getHeight() >> 1), GameView.paintForBitmaps);
    }
    public void drawEntityDescription(Canvas c, float startY, float height) {
        GameView.paintForTexts.setColor(Color.RED);
        c.drawText("" + health + "/" + maxHealth, GameView.screenWidth * 0.5f, startY + GameView.paintForTexts.getTextSize(), GameView.paintForTexts);
        c.drawBitmap(Bitmap.createScaledBitmap(linkedPlayer.getIconBitmap(),(int) (height * 0.8f),(int) (height * 0.8f), false), GameView.screenWidth * 0.6f, startY + height * 0.1f, GameView.paintForBitmaps);
    }
    public Bitmap getIconBitmap() {
        return animationSprites[0];
    }
    public Player getLinkedPlayer() {
        return linkedPlayer;
    }
    public void setLinkedPlayer(Player p) {
        linkedPlayer = p;
    }
    public boolean tapEntityDescription() {
        return false;
    }
    private Bitmap getNextSprite() {
        currentSprite = (currentSprite + 1) % animationSprites.length;
        return animationSprites[currentSprite];
    }
    public abstract Action getDefaultAction();
    public abstract void setDefaultAction(Action action);

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
}
