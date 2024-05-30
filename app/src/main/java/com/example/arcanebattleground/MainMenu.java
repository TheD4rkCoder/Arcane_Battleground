package com.example.arcanebattleground;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MainMenu extends SurfaceView implements SurfaceHolder.Callback {
    /*
    Intent intent = new Intent(MainActivity.mainActivity, GameActivity.class);
    MainActivity.startActivity(intent);
     */
    private static SurfaceHolder holder;

    public MainMenu(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

    }

    public static void drawMenu() {
        //should always be first:
        Canvas c = holder.lockCanvas();

        // should always be last:
        holder.unlockCanvasAndPost(c);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // do smth different for online i guess
        if (Animation.isAnimationPlaying())
            return true;
        boolean endMove = false;
        if (event.getAction() == MotionEvent.ACTION_UP) {

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
