package com.example.arcanebattleground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Bitmap boardBitmap;
    private Paint paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample;
    public int screenHeight, screenWidth;
    private ArrayList<CustomRect> rectsToDraw = new ArrayList<>();
    private ArrayList<CustomCircle> circlesToDraw = new ArrayList<>();
    public GameView(Context context) {
        super(context);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        // getting a .png from drawable folder:
        boardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hexagonboard2);
        // last argument (boolean) if for whether you want to use some sort of gaussian filter (if your scaled up image should be pixelated: false, otherwise: ture)
        boardBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hexagonboard2), screenWidth, (int)(screenHeight*0.8), false);

        paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample = new Paint();
        paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample.setAlpha(0); // may be needed for some semitransparent spells or smth like that
        paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample.setAntiAlias(false); // needed in 'drawBitmap' if pixel-art gets distorted when drawing
        paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample.setDither(false); // not sure anymore
        paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample.setFilterBitmap(false); // for the same reason as the 2 previous ones
        paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample.setColor(Color.RED); // color for drawline, drawcircle ect.
        paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample.setTextSize(120); // well, what do you think it does? //Is it american?
        paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample.setTypeface(getResources().getFont(R.font.dpcomic)); // set font for drawText()

        holder = getHolder();
        holder.addCallback(this);
    }

    private void drawGame() {
        //should always be first:
        Canvas c = holder.lockCanvas();
        String text = "perchance";


        c.drawBitmap(boardBitmap, 0, 0, null); // paint doesn't matter much for bitmaps/images (but is important for rect, font ect.)

        Paint rectPaint = new Paint();
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(Color.LTGRAY);


        c.drawRect(0.02f*screenWidth, 0.81f*screenHeight, 0.23f*screenWidth, 0.98f*screenHeight, rectPaint);
        c.drawRect(0.27f*screenWidth, 0.81f*screenHeight, 0.48f*screenWidth, 0.98f*screenHeight, rectPaint);
        c.drawRect(0.52f*screenWidth, 0.81f*screenHeight, 0.73f*screenWidth, 0.98f*screenHeight, rectPaint);
        c.drawRect(0.77f*screenWidth, 0.81f*screenHeight, 0.98f*screenWidth, 0.98f*screenHeight, rectPaint);
        // border (not sure if you ever need it):
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setColor(Color.GRAY);
        rectPaint.setStrokeWidth(0.02f*screenWidth);
        c.drawRect(0.02f*screenWidth, 0.81f*screenHeight, 0.23f*screenWidth, 0.98f*screenHeight, rectPaint);
        c.drawRect(0.27f*screenWidth, 0.81f*screenHeight, 0.48f*screenWidth, 0.98f*screenHeight, rectPaint);
        c.drawRect(0.52f*screenWidth, 0.81f*screenHeight, 0.73f*screenWidth, 0.98f*screenHeight, rectPaint);
        c.drawRect(0.77f*screenWidth, 0.81f*screenHeight, 0.98f*screenWidth, 0.98f*screenHeight, rectPaint);

        rectPaint.setColor(Color.GREEN);
        rectPaint.setStyle(Paint.Style.FILL);
        for(CustomRect r : rectsToDraw){
            c.drawRect(r.left * screenWidth, r.top * screenHeight, r.right * screenWidth, r.bottom * screenHeight, rectPaint);
        }
        for(CustomCircle cir : circlesToDraw)
            c.drawCircle(cir.centerX * screenWidth, cir.centerY * screenHeight, cir.radius * screenWidth, rectPaint);

        c.drawCircle(0.0684f * 3.25f * screenWidth, 0.079f * screenHeight, 0.02f * screenWidth, rectPaint);
        c.drawText(text, 100, screenHeight*0.9f - paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample.getTextSize(), paintForBitmapsIfYouWantToTryAndAlsoSomeSpecificTextAsExample);


        // should always be last:
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.performClick();
        // click position:
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) { // MotionEvent has all the possible actions (if you need it to be only on drag or smth)
            // do smth
            circlesToDraw.add(new CustomCircle(x/screenWidth, y/screenHeight, 0.05f));
        }
        // redraw the canvas to show the next frame:
        drawGame();
        return true; // touch already handled (for event bubbling)
    }

    @Override
    public boolean performClick() {
        return super.performClick();
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
