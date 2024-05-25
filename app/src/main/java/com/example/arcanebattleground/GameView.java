package com.example.arcanebattleground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.arcanebattleground.Actions.Action;
import com.example.arcanebattleground.Actions.DefaultPlayerAction;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static Bitmap oldSpriteSheet, spellSpriteSheet, cancelBitmap, windIconBitmap;
    public static float hexagonWidth, hexagonHeight;
    public static Paint paintForBitmaps, paintForTexts, paintForShapes;
    public static int screenHeight, screenWidth;
    private SurfaceHolder holder;
    private Bitmap boardBitmap;
    private ArrayList<CustomRect> rectsToDraw = new ArrayList<>();
    private ArrayList<CustomCircle> circlesToDraw = new ArrayList<>();
    private static final ArrayList<GameEntity> entities = new ArrayList<>();
    private static int currentEntitiesTurn = 0;
    public static Action currentAction;
    private boolean animationPlaying = false;
    private Bitmap selectedEntityBitmap;

    public GameView(Context context) {
        super(context);
        oldSpriteSheet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.oldspritesheet), 192, 192, false);
        spellSpriteSheet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.spell_sprites_2), 584, 828, false);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        cancelBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel), (int)(screenWidth*0.225), (int)(screenWidth*0.225), false);
        windIconBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wind), (int)(screenWidth*0.225), (int)(screenWidth*0.225), false);

        // getting a .png from drawable folder:
        boardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hexagonboard2);
        // last argument (boolean) if for whether you want to use some sort of gaussian filter (if your scaled up image should be pixelated: false, otherwise: ture)
        boardBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hexagonboard2), screenWidth, (int) (screenHeight * 0.8), false);
        hexagonWidth = boardBitmap.getWidth() / 6.47f;
        hexagonHeight = boardBitmap.getHeight() / 13.32f;


        entities.add(new Player(new Bitmap[]{Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 32, 32, 32, 32), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false)}));
        entities.get(0).setY(11);
        entities.get(0).setX(4);
        entities.add(new Player(new Bitmap[]{Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.oldSpriteSheet, 64, 64, 128, 128), (int) (hexagonWidth * 0.7f), (int) (hexagonWidth * 0.7f), false)}));
        entities.get(1).setY(1);
        entities.get(0).setDefaultAction(new DefaultPlayerAction());
        currentAction = entities.get(currentEntitiesTurn).getDefaultAction();

        selectedEntityBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.spellSpriteSheet, 277, 88, 61, 62), (int) (hexagonWidth * 0.9f), (int) (hexagonWidth * 0.9f), false);
        Player.castIndicatorBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gem_ruby_large), (int) (hexagonWidth * 0.2f), (int) (hexagonWidth * 0.2f), false);
        Player.castButtonBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(GameView.spellSpriteSheet, 404, 86, 128, 128), (int) (hexagonWidth * 0.9f), (int) (hexagonWidth * 0.9f), false);

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

    public void endMove() {
        currentEntitiesTurn = (currentEntitiesTurn + 1) % entities.size();
        resetToDefaultAction();
    }
    public static void resetToDefaultAction() {
        currentAction = entities.get(currentEntitiesTurn).getDefaultAction();
    }

    public static int getDistance(int x1, int x2, int y1, int y2) {
        if (Math.abs(y1 - y2) >> 1 >= Math.abs(x1 - x2))
            return Math.abs(y1 - y2);
        if (Math.abs(y1 - y2) % 2 == 1 && ((y1 % 2 == 0 && x2 < x1) || (y1 % 2 == 1 && x1 < x2))) {
            return Math.abs(y1 - y2) + Math.abs(x1 - x2) - 1 - (Math.abs(y1 - y2) >> 1);
        }
        return Math.abs(y1 - y2) + Math.abs(x1 - x2) - (Math.abs(y1 - y2) >> 1);
    }
    public static ArrayList<GameEntity> getCollidingEntities(int x, int y) {
        ArrayList<GameEntity> list = new ArrayList<>();
        for (GameEntity e : entities) {
            if (e.getX() == x && e.getY() == y)
                list.add(e);
        }
        return list;
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

        entities.get(currentEntitiesTurn).drawEntityDescription(c, boardBitmap.getHeight(), screenHeight - boardBitmap.getHeight() - (screenWidth >> 2));

        currentAction.drawDescription(c, screenHeight - (screenWidth >> 2), (screenWidth >> 2), entities.get(currentEntitiesTurn));

        for (int i = 0; i < entities.size(); i++) {
            GameEntity e = entities.get(i);
            float centerX = hexagonWidth * (e.getX() - 0.04f + ((e.getY() % 2 == 0) ? 0.5f : 1f));
            float centerY = hexagonHeight * (e.getY() + 0.65f);
            if (i == currentEntitiesTurn) {
                drawPossibleActionCircles(c, e.getX(), e.getY(), currentAction.radius);
                c.drawBitmap(selectedEntityBitmap, centerX - (selectedEntityBitmap.getWidth() >> 1), centerY - (selectedEntityBitmap.getHeight() >> 1), paintForBitmaps);
            }
            e.drawOnBoard(c, centerX, centerY);
        }
        // TODO: implement something for Animations

        // should always be last:
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // do smth different for online i guess
        if (animationPlaying)
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
        }
        if (endMove)
            endMove();
        // redraw the canvas to show the next frame:
        drawGame(); // should / will be given to another thread (for animations)
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
