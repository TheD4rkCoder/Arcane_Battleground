package com.example.arcanebattleground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.arcanebattleground.Actions.Action;
import com.example.arcanebattleground.Actions.DefaultPlayerAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static Bitmap oldSpriteSheet, spellSpriteSheet, cancelBitmap, windIconBitmap, tier1SprintSpellBitmap, tier10GeneOptimizationBitmap, spellSlotBitmap, meditationAnimationBitmap;
    public static float hexagonWidth, hexagonHeight;
    public static Paint paintForBitmaps, paintForTexts, paintForShapes;
    public static int screenHeight, screenWidth;
    public static Action currentAction;
    public static GameLoop gameLoop;
    private static SurfaceHolder holder;
    private static Bitmap boardBitmap, lavaBackgroundBitmap, selectedEntityBitmap;
    private static final ArrayList<GameEntity> entities = new ArrayList<>();
    private static int currentEntitiesTurn = 0;

    public GameView(Context context) {
        super(context);
        oldSpriteSheet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.oldspritesheet), 192, 192, false);
        spellSpriteSheet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spell_sprites_2), 584, 828, false);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        cancelBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel), 16, 16, false);
        cancelBitmap = Bitmap.createScaledBitmap(cancelBitmap, (int) (screenWidth * 0.225), (int) (screenWidth * 0.225), false);

        windIconBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wind), (int) (screenWidth * 0.225), (int) (screenWidth * 0.225), false);

        // getting a .png from drawable folder:
        boardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hexagonboard3);
        // last argument (boolean) if for whether you want to use some sort of gaussian filter (if your scaled up image should be pixelated: false, otherwise: ture)
        boardBitmap = Bitmap.createScaledBitmap(boardBitmap, screenWidth, (int) (screenHeight - screenWidth * 0.45f), false);
        lavaBackgroundBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lava_background), 64, 128, false);
        lavaBackgroundBitmap = Bitmap.createScaledBitmap(lavaBackgroundBitmap, screenWidth, boardBitmap.getHeight(), false);

        hexagonWidth = boardBitmap.getWidth() / 6.47f;
        hexagonHeight = boardBitmap.getHeight() / 13.32f;

        tier1SprintSpellBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.swiftness);
        tier10GeneOptimizationBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gene_optimization);
        spellSlotBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spell_slot), (int)(screenWidth*0.225), (int)(screenWidth*0.225), false);
        meditationAnimationBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mana_ring), (int)(2*hexagonWidth), (int)(2*hexagonHeight), false);

        entities.add(new Player(new Bitmap[]{
                Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 32, 32, 32, 32), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 65, 33, 30, 30), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 98, 34, 28, 28), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 129, 33, 30, 30), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false)
        }));
        entities.get(0).setY(11);
        entities.get(0).setX(4);
        entities.add(new Player(new Bitmap[]{
                Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 32, 0, 32, 32), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 65, 1, 30, 30), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 98, 2, 28, 28), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false),
                Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 129, 1, 30, 30), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false)
        }));
        entities.get(1).setY(1);
        entities.get(0).setDefaultAction(new DefaultPlayerAction());
        currentAction = entities.get(currentEntitiesTurn).getDefaultAction();

        selectedEntityBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.spellSpriteSheet, 277, 88, 61, 62), (int) (hexagonWidth * 0.9f), (int) (hexagonWidth * 0.9f), false);
        Player.castIndicatorBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gem_ruby_large), (int) (hexagonWidth * 0.2f), (int) (hexagonWidth * 0.2f), false);
        Player.castButtonBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.spellSpriteSheet, 404, 86, 128, 128), (int) (hexagonWidth * 0.9f), (int) (hexagonWidth * 0.9f), false);

        Player.castAnimation = new Bitmap[5];
        Player.castAnimation[0] = Bitmap.createScaledBitmap(Player.castButtonBitmap, (int) (hexagonWidth * 2f), (int) (hexagonWidth * 2f), false);

        Matrix m = new Matrix();
        m.postRotate(10);
        Player.castAnimation[1] = Bitmap.createBitmap(Player.castAnimation[0], 0, 0, Player.castAnimation[0].getWidth(), Player.castAnimation[0].getHeight(), m, false);
        m.postRotate(10);
        Player.castAnimation[2] = Bitmap.createBitmap(Player.castAnimation[0], 0, 0, Player.castAnimation[0].getWidth(), Player.castAnimation[0].getHeight(), m, false);
        m.postRotate(10);
        Player.castAnimation[3] = Bitmap.createBitmap(Player.castAnimation[0], 0, 0, Player.castAnimation[0].getWidth(), Player.castAnimation[0].getHeight(), m, false);
        m.postRotate(10);
        Player.castAnimation[4] = Bitmap.createBitmap(Player.castAnimation[0], 0, 0, Player.castAnimation[0].getWidth(), Player.castAnimation[0].getHeight(), m, false);


        paintForBitmaps = new Paint();
        paintForBitmaps.setAntiAlias(false); // needed in 'drawBitmap' if pixel-art gets distorted when drawing
        paintForBitmaps.setDither(false); // not sure anymore
        paintForBitmaps.setFilterBitmap(false); // for the same reason as the 2 previous ones

        paintForTexts = new Paint();
        paintForTexts.setColor(Color.RED); // color for drawline, drawcircle ect.
        paintForTexts.setTextSize(screenWidth * 0.1f); // well, what do you think it does? //Is it american?
        paintForTexts.setTypeface(getResources().getFont(R.font.dpcomic)); // set font for drawText()
        paintForTexts.setTextAlign(Paint.Align.RIGHT);

        paintForShapes = new Paint();

        holder = getHolder();
        holder.addCallback(this);

    }

    public static void endMove() {
        currentEntitiesTurn = (currentEntitiesTurn + 1) % entities.size();
        resetToDefaultAction();
    }

    public static void resetToDefaultAction() {
        currentAction = entities.get(currentEntitiesTurn).getDefaultAction();
    }

    public static int getDistance(int x1, int x2, int y1, int y2) {
        if (Math.abs(y1 - y2) >> 1 >= Math.abs(x1 - x2))
            return Math.abs(y1 - y2);
        int dist = Math.abs(y1 - y2) + Math.abs(x1 - x2) - (Math.abs(y1 - y2) >> 1);
        if (Math.abs(y1 - y2) % 2 == 1 && ((y1 % 2 == 0 && x2 < x1) || (y1 % 2 == 1 && x1 < x2))) {
            return dist - 1;
        }
        return dist;
    }

    public static ArrayList<GameEntity> getCollidingEntities(int x, int y) {
        ArrayList<GameEntity> list = new ArrayList<>();
        for (GameEntity e : entities) {
            if (e.getX() == x && e.getY() == y)
                list.add(e);
        }
        return list;
    }

    private static void drawPossibleActionCircles(Canvas c, int centerX, int centerY, int radius) {
        paintForShapes.setColor(Color.GRAY);
        paintForShapes.setAlpha(200);
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

    public static void startAnimation(int startX, int startY, int endX, int endY, Bitmap[] animationSprites, boolean stayAfterEnd, List<GameEntity> entitiesToHide) {
        Animation.startX = hexagonWidth * (startX - 0.04f + ((startY % 2 == 0) ? 0.5f : 1f));
        Animation.startY = hexagonHeight * (startY + 0.65f);
        Animation.endX = hexagonWidth * (endX - 0.04f + ((endY % 2 == 0) ? 0.5f : 1f));
        Animation.endY = hexagonHeight * (endY + 0.65f);
        Animation.stayAfterEnd = stayAfterEnd;
        Animation.animationSprites = animationSprites;
        Animation.entitiesToHide = entitiesToHide;
        for (GameEntity e : entitiesToHide) {
            e.setVisibility(false);
        }
        Animation.currentAnimationCounter = 0;
    }

    public static void startAnimation(int startX, int startY, int endX, int endY, Bitmap[] animationSprites, boolean stayAfterEnd) {
        startAnimation(startX, startY, endX, endY, animationSprites, stayAfterEnd, Collections.emptyList());
    }

    public static void drawGame() {
        //should always be first:
        Canvas c = holder.lockCanvas();
        c.drawBitmap(lavaBackgroundBitmap, 0, 0, paintForBitmaps);
        c.drawBitmap(boardBitmap, 0, 0, paintForBitmaps); // paint doesn't matter much for bitmaps/images (but is important for rect, font ect.)

        paintForShapes.setColor(Color.DKGRAY);
        c.drawRect(0, boardBitmap.getHeight(), screenWidth, screenHeight, paintForShapes);

        entities.get(currentEntitiesTurn).drawEntityDescription(c, boardBitmap.getHeight(), screenHeight - boardBitmap.getHeight() - (screenWidth >> 2));

        currentAction.drawDescription(c, screenHeight - (int) (screenWidth * 0.27f), (int) (screenWidth * 0.27f), entities.get(currentEntitiesTurn));


        if (Animation.isAnimationPlaying()) {
            Bitmap b = Animation.getNextAnimationBitmap();
            c.drawBitmap(b, Animation.getCurrentX() - (b.getWidth() >> 1), Animation.getCurrentY() - (b.getHeight() >> 1), paintForBitmaps);
        } else if (Animation.stayAfterEnd) {
            Bitmap b = Animation.getNextAnimationBitmap();
            c.drawBitmap(b, Animation.endX - (b.getWidth() >> 1), Animation.endY - (b.getHeight() >> 1), paintForBitmaps);
        }

        for (int i = 0; i < entities.size(); i++) {
            GameEntity e = entities.get(i);
            float centerX = hexagonWidth * (e.getX() - 0.04f + ((e.getY() % 2 == 0) ? 0.5f : 1f));
            float centerY = hexagonHeight * (e.getY() + 0.65f);
            if (i == currentEntitiesTurn && !Animation.isAnimationPlaying()) {
                drawPossibleActionCircles(c, e.getX(), e.getY(), currentAction.radius); // only in your turn / always if not playing online
                c.drawBitmap(selectedEntityBitmap, centerX - (selectedEntityBitmap.getWidth() >> 1), centerY - (selectedEntityBitmap.getHeight() >> 1), paintForBitmaps);
            }
            e.drawOnBoard(c, centerX, centerY);
        }


        // should always be last:
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // do smth different for online i guess
        if (Animation.isAnimationPlaying())
            return true;
        boolean endMove = false;
        if (event.getAction() == MotionEvent.ACTION_UP) { // MotionEvent has all the possible actions (if you need it to be only on drag or smth)
            if (event.getY() < boardBitmap.getHeight()) {
                int fieldY = (int) ((event.getY() - 0.15f * hexagonHeight) / hexagonHeight);
                int fieldX = (int) ((event.getX() - hexagonWidth * ((fieldY % 2 == 0) ? 0f : 0.5f)) / hexagonWidth);
                if (fieldX < 0 || fieldX > 5 || fieldY < 0 || fieldY > 12)
                    return true;

                endMove = currentAction.boardTap(fieldX, fieldY, entities.get(currentEntitiesTurn));

            } else if (event.getY() > screenHeight - (screenWidth >> 2)) {
                endMove = currentAction.descriptionTap(event.getX(), entities.get(currentEntitiesTurn));
            } else {
                endMove = entities.get(currentEntitiesTurn).tapEntityDescription();
            }
            if (endMove)
                endMove();
            // redraw the canvas to show the next frame:
            drawGame();
        }

        return true; // touch already handled (for event bubbling)
    }


    // Callback-Functions (just ignore):
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop = new GameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
