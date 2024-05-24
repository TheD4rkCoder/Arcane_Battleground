package com.example.arcanebattleground;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class HoneyCombView extends View {
    private Paint paint = new Paint();

    public HoneyCombView(Context context) {
        super(context);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get screen dimensions
        int width = getWidth();
        int height = getHeight();

        // Calculate half stroke width for border overlap adjustments

        // Calculate optimal radius considering half stroke width
        float radius = Math.min(width / (7f), height / (9f));

        // Adjust calculation for fitting columns and rows with overlapping borders
        int columns = (int) Math.ceil((float) width / (2 * radius));
        int rows = (int) Math.ceil((float) height / (1.75f * radius));

        // Loop through each hexagon, adjusting for downward orientation
        for (int i = rows - 1; i >= 0; i--) {
            float offset = (i % 2 == 1) ? radius : 0;
            for (int j = 0; j < columns; j++) {
                // Adjust for complete top-right hexagon visibility and border overlap
                float drawX = j * 2 * radius + offset;
                float drawY = i * 1.75f * radius;
                drawHexagon(canvas, drawX, drawY, radius);
            }
        }
    }

    private void drawHexagon(Canvas canvas, float centerX, float centerY, float radius) {
        // Calculate points for downward-oriented hexagon
        float[] path = new float[12];
        float angle = (float) (90f / 180f * Math.PI); // Start angle for downward orientation
        float step = (float) (60f / 180f * Math.PI);
        for (int i = 0; i < path.length; i += 2) {
            path[i] = (float) (centerX + radius * Math.cos(angle));
            path[i + 1] = (float) (centerY + radius * Math.sin(angle));
            angle += step;
        }
        Path pathVar = new Path();
        // Move to the first point in the path array
        pathVar.moveTo(path[0], path[1]);
        // Loop through the remaining points and add them to the path
        for (int i = 2; i < path.length; i += 2) {
            pathVar.lineTo(path[i], path[i + 1]);
        }
        // Close the path (optional, but recommended for a filled hexagon)
        pathVar.close();
        // Draw the path on the canvas
        canvas.drawPath(pathVar, paint);
    }
}