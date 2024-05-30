package com.example.arcanebattleground;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MainMenu extends SurfaceView implements SurfaceHolder.Callback {
    private static SurfaceHolder holder;
    private Paint paintForTexts;
    private int screenWidth, screenHeight;

    public MainMenu(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        paintForTexts = new Paint();
        paintForTexts.setColor(Color.RED);
        paintForTexts.setTextSize(screenWidth * 0.1f);
        paintForTexts.setTypeface(getResources().getFont(R.font.dpcomic));
        paintForTexts.setTextAlign(Paint.Align.CENTER);
    }

    public void drawMenu() {
        //should always be first:
        Canvas c = holder.lockCanvas();
        // TODO: draw background

        // TODO: field for entering id, ect
        c.drawText("Offline Play", screenWidth * 0.5f, screenHeight * 0.2f, paintForTexts);

        c.drawText("Create lobby", screenWidth * 0.5f, screenHeight * 0.4f, paintForTexts);

        c.drawText("Join lobby", screenWidth * 0.5f, screenHeight * 0.6f, paintForTexts);

        // should always be last:
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getY() > screenHeight * 0.2 - paintForTexts.getTextSize() && event.getY() < screenHeight * 0.2) {
                Intent intent = new Intent(MainActivity.mainActivity, GameActivity.class);
                MainActivity.mainActivity.startActivity(intent);
            } else if (event.getY() > screenHeight * 0.4- paintForTexts.getTextSize() && event.getY() < screenHeight * 0.4) {
            } else if (event.getY() > screenHeight * 0.6- paintForTexts.getTextSize() && event.getY() < screenHeight * 0.6) {
            }
        }

        return true; // touch already handled (for event bubbling)
    }


    // Callback-Functions (just ignore):
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawMenu();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
