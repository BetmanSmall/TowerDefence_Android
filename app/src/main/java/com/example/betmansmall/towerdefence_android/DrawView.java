package com.example.betmansmall.towerdefence_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
        canvas.drawLine(getWidth(), 0, 0, getHeight(), paint);
    }
}
