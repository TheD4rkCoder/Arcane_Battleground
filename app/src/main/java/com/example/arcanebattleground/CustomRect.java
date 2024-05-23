package com.example.arcanebattleground;

public class CustomRect {
    public float left;
    public float top;
    public float right;
    public float bottom;
    public CustomRect(float x, float y){
        left = x- 0.02f;
        right = x  + 0.02f;
        top = y- 0.02f;
        bottom = y + 0.02f;
        if (left < 0)
            left = 0;
        if (right < 0)
            right = 0;
        if (top < 0)
            top = 0;
        if (bottom < 0)
            bottom = 0;
    }
}
