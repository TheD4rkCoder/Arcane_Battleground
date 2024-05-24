package com.example.arcanebattleground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static Bitmap oldSpriteSheet;
    public static Bitmap spellSpriteSheet;
    private SurfaceHolder holder;
    private Bitmap boardBitmap;
    private float hexagonWidth;
    private float hexagonHeight;
    private Paint paintForBitmaps;
    private Paint paintForTexts;
    private Paint paintForShapes;
    private int screenHeight, screenWidth;
    private ArrayList<CustomRect> rectsToDraw = new ArrayList<>();
    private ArrayList<CustomCircle> circlesToDraw = new ArrayList<>();
    private Player[] players = new Player[2];
    private int currentTurn = 0;
    private boolean animationPlaying = false;
    private Bitmap selectedEntityBitmap;
    private Bitmap castIndicatorBitmap;
    private Bitmap castButtonBitmap;

    public GameView(Context context) {
        super(context);
        oldSpriteSheet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.oldspritesheet), 192, 192, false);
        spellSpriteSheet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spell_sprites_2), 584, 828, false);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;


        // getting a .png from drawable folder:
        boardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hexagonboard2);
        // last argument (boolean) if for whether you want to use some sort of gaussian filter (if your scaled up image should be pixelated: false, otherwise: ture)
        boardBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hexagonboard2), screenWidth, (int) (screenHeight * 0.8), false);
        hexagonWidth = boardBitmap.getWidth() / 6.47f;
        hexagonHeight = boardBitmap.getHeight() / 13.32f;

        players[0] = new Player(Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 32, 32, 32, 32), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false));
        players[0].setY(11);
        players[0].setX(4);
        players[1] = new Player(Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 64, 64, 128, 128), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false));
        players[1].setY(1);

        selectedEntityBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.spellSpriteSheet, 277, 88, 61, 62), (int) (hexagonWidth * 0.9f), (int) (hexagonWidth * 0.9f), false);
        castIndicatorBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gem_ruby_large), (int) (hexagonWidth * 0.2f), (int) (hexagonWidth * 0.2f), false);
        castButtonBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.spellSpriteSheet, 404, 86, 128, 128), (int) (hexagonWidth * 0.9f), (int) (hexagonWidth * 0.9f), false);

        paintForBitmaps = new Paint();
        paintForBitmaps.setAntiAlias(false); // needed in 'drawBitmap' if pixel-art gets distorted when drawing
        paintForBitmaps.setDither(false); // not sure anymore
        paintForBitmaps.setFilterBitmap(false); // for the same reason as the 2 previous ones

        paintForTexts = new Paint();
        paintForTexts.setColor(Color.RED); // color for drawline, drawcircle ect.
        paintForTexts.setTextSize(screenWidth*0.1f); // well, what do you think it does? //Is it american?
        paintForTexts.setTypeface(getResources().getFont(R.font.dpcomic)); // set font for drawText()
        paintForTexts.setTextAlign(Paint.Align.RIGHT);

        paintForShapes = new Paint();

        holder = getHolder();
        holder.addCallback(this);
    }

    public static int getDistance(int x1, int x2, int y1, int y2) {
        if (Math.abs(y1 - y2) >> 1 >= Math.abs(x1 - x2))
            return Math.abs(y1 - y2);
        if (Math.abs(y1 - y2) % 2 == 1 && ((y1 % 2 == 0 && x2 < x1) || (y1 % 2 == 1 && x1 < x2))) {
            return Math.abs(y1 - y2) + Math.abs(x1 - x2) - 1 - (Math.abs(y1 - y2) >> 1);
        }
        return Math.abs(y1 - y2) + Math.abs(x1 - x2) - (Math.abs(y1 - y2) >> 1);
    }

    private void drawPossibleActionCircles(Canvas c, int centerX, int centerY, int radius) {
        paintForShapes.setColor(Color.GRAY);
        paintForShapes.setAlpha(150);
        int start, end;
        for (int y = centerY - radius; y <= centerY + radius; y++) {
            if (y % 2 == 0) {
                start = centerX - radius + ((Math.abs(y - centerY) + 1) >> 1);
                end = centerX + radius - ((Math.abs(y - centerY)) >> 1);
            } else {
                start = centerX - radius + ((Math.abs(y - centerY)) >> 1);
                end = centerX + radius - ((Math.abs(y - centerY) + 1) >> 1);
            }
            for (int x = start; x <= end; x++) {
                if ((x == centerX && y == centerY) || x < 0 || y < 0 || x > 5 || y > 12)
                    continue;
                c.drawCircle(hexagonWidth * (x - 0.04f + ((y % 2 == 0) ? 0.5f : 1f)), hexagonHeight * (y + 0.65f), hexagonWidth * 0.2f, paintForShapes);
            }
        }
        paintForShapes.setAlpha(255);
    }

    private void drawGame() {
        //should always be first:
        Canvas c = holder.lockCanvas();


        c.drawBitmap(boardBitmap, 0, 0, null); // paint doesn't matter much for bitmaps/images (but is important for rect, font ect.)

        paintForShapes.setColor(Color.LTGRAY);
        c.drawRect(0, boardBitmap.getHeight(), screenWidth, screenHeight, paintForShapes);

        paintForShapes.setColor(Color.GRAY);
        c.drawRect(0.02f * screenWidth, screenHeight - 0.245f * screenWidth, 0.245f * screenWidth, screenHeight - 0.02f * screenWidth, paintForShapes);
        c.drawRect(0.265f * screenWidth, screenHeight - 0.245f * screenWidth, 0.49f * screenWidth, screenHeight - 0.02f * screenWidth, paintForShapes);
        c.drawRect(0.51f * screenWidth, screenHeight - 0.245f * screenWidth, 0.735f * screenWidth, screenHeight - 0.02f * screenWidth, paintForShapes);
        c.drawRect(0.755f * screenWidth, screenHeight - 0.245f * screenWidth, 0.98f * screenWidth, screenHeight - 0.02f * screenWidth, paintForShapes);

        paintForTexts.setColor(Color.RED);
        c.drawText("" + players[currentTurn].getHealth() + "/" + players[currentTurn].getMaxHealth(), screenWidth * 0.35f, boardBitmap.getHeight() + paintForTexts.getTextSize(), paintForTexts);
        paintForTexts.setColor(Color.BLUE);
        c.drawText("" + players[currentTurn].getHealth() + "/" + players[currentTurn].getMaxMana(), screenWidth * 0.97f, boardBitmap.getHeight() + paintForTexts.getTextSize(), paintForTexts);
        c.drawBitmap(castButtonBitmap, screenWidth * 0.5f - (castButtonBitmap.getWidth()>>1), boardBitmap.getHeight() + 20, paintForBitmaps);

        for (int i = 0; i < players.length; i++) {
            Player p = players[i];
            Bitmap b = p.getIdleSprite();
            if (i == currentTurn) {
                drawPossibleActionCircles(c, p.getX(), p.getY(), 1);
                c.drawBitmap(selectedEntityBitmap, hexagonWidth * (p.getX() - 0.04f + ((p.getY() % 2 == 0) ? 0.5f : 1f)) - (selectedEntityBitmap.getWidth() >> 1), hexagonHeight * (p.getY() + 0.65f) - (selectedEntityBitmap.getHeight() >> 1), paintForBitmaps);
            }
            for (int j = 0; j < p.getRoundsCast(); j++) {
                c.drawBitmap(castIndicatorBitmap, hexagonWidth * (p.getX() - 0.04f + ((p.getY() % 2 == 0) ? 0.5f : 1f)) - (((p.getRoundsCast() - 2 * j) * castIndicatorBitmap.getWidth()) >> 1), hexagonHeight * (p.getY() + 0.1f) - (castIndicatorBitmap.getHeight() >> 1), paintForBitmaps);
            }
            c.drawBitmap(b, hexagonWidth * (p.getX() - 0.04f + ((p.getY() % 2 == 0) ? 0.5f : 1f)) - (b.getWidth() >> 1), hexagonHeight * (p.getY() + 0.65f) - (b.getHeight() >> 1), paintForBitmaps);
        }

        /*
        // if we're using Bitmaps for everything, what's the use of this?
        paintForShapes.setColor(Color.GREEN);
        paintForShapes.setStyle(Paint.Style.FILL);
        for (CustomRect r : rectsToDraw) {
            c.drawRect(r.left * screenWidth, r.top * screenHeight, r.right * screenWidth, r.bottom * screenHeight, paintForShapes);
        }
        for (CustomCircle cir : circlesToDraw)
            c.drawCircle(cir.centerX * screenWidth, cir.centerY * screenHeight, cir.radius * screenWidth, paintForShapes);
         */

        // should always be last:
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // do smth different for online i guess
        if (animationPlaying)
            return true;
        if (event.getAction() == MotionEvent.ACTION_DOWN) { // MotionEvent has all the possible actions (if you need it to be only on drag or smth)
            if (event.getY() < boardBitmap.getHeight() - 0.28 * hexagonHeight && event.getY() > hexagonHeight * 0.28) {
                int fieldY = (int) ((event.getY() - 0.15f * hexagonHeight) / hexagonHeight);
                int fieldX = (int) ((event.getX() - hexagonWidth * ((fieldY % 2 == 0) ? 0f : 0.5f)) / hexagonWidth);
                if (getDistance(players[currentTurn].getX(), fieldX, players[currentTurn].getY(), fieldY) == 0) {
                    // meditate
                } else if (getDistance(players[currentTurn].getX(), fieldX, players[currentTurn].getY(), fieldY) == 1) {
                    players[currentTurn].setX(fieldX);
                    players[currentTurn].setY(fieldY);
                }
            } else if (event.getY() > screenHeight - (screenWidth >> 4)) {
                // open spell window or smth
                if (event.getX() < 0.25f * screenWidth) {
                } else if (event.getX() < 0.5f * screenWidth) {
                } else if (event.getX() < 0.75f * screenWidth) {
                } else {
                }
            } else {
                players[currentTurn].setRoundsCast(players[currentTurn].getRoundsCast() + 1);
                currentTurn = (currentTurn + 1) % players.length;
            }
        }
        // redraw the canvas to show the next frame:
        drawGame();
        return true; // touch already handled (for event bubbling)
    }


    // Callback-Functions (just ignore):
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawGame();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
