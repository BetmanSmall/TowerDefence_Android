package com.example.betmansmall.towerdefence_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();
    Field field = new Field();

    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.BLACK);

//        Log.d("TTW", "getWidth(): " + getWidth());
//        Log.d("TTW", "getHeight(): " + getHeight());

//        int sizeCell = 32;
//        int sizeX = (int) (getWidth()/sizeCell); // 25; // 65, 120
//        int sizeY = (int) (getHeight()/sizeCell); // 11; // 30, 60
        int sizeX = 25; // 65, 120
        int sizeY = 11; // 30, 60

        field = new Field();
        field.createField(sizeX, sizeY); // 65, 30
//        field.setSizeCell(sizeCell);

        Log.d("TTW", "field.sizeX(): " + field.getSizeX());
        Log.d("TTW", "field.sizeY(): " + field.getSizeY());
        for(int x = 0; x < field.getSizeX(); x++)
            for(int y = 0; y < field.getSizeY(); y++)
                if(Math.random()*101 <= 30)
                    field.setBusy(x, y);
    }

    public void mousePressEvent(float mouseX, float mouseY) {
        int x = (int) mouseX;
        int y = (int) mouseY;
        int mainCoorMapX = field.getMainCoorMapX();
        int mainCoorMapY = field.getMainCoorMapY();
        int spaceWidget = field.getSpaceWidget();
        int sizeCell = field.getSizeCell();

        int tmpX, tmpY;
        tmpX = ( (x+sizeCell - spaceWidget - mainCoorMapX) / sizeCell);
        tmpY = ( (y+sizeCell - spaceWidget - mainCoorMapY) / sizeCell);
        if(tmpX > 0 && tmpX < field.getSizeX()+1)
            if(tmpY > 0 && tmpY < field.getSizeY()+1)
            {
                x = tmpX-1;
                y = tmpY-1;
            }

        Log.d("TTW", "mousePressEvent() -- mouseX: " + x + " mouseY: " + y);
        if(field.containEmpty(x, y)) {
            field.setBusy(x, y);
        } else if(field.containBusy(x, y)) {
            field.clearBusy(x, y);
        }
        invalidate();
    }

//    public boolean whichCell(int &mouseX, int &mouseY) {
//        int mainCoorMapX = field.getMainCoorMapX();
//        int mainCoorMapY = field.getMainCoorMapY();
//        int spaceWidget = field.getSpaceWidget();
//        int sizeCell = field.getSizeCell();
//
//        int tmpX, tmpY;
//        tmpX = ( (mouseX+sizeCell - spaceWidget - mainCoorMapX) / sizeCell);
//        tmpY = ( (mouseY+sizeCell - spaceWidget - mainCoorMapY) / sizeCell);
//        if(tmpX > 0 && tmpX < field.getSizeX()+1)
//            if(tmpY > 0 && tmpY < field.getSizeY()+1)
//            {
//                mouseX = tmpX-1;
//                mouseY = tmpY-1;
//                return true;
//            }
//
//        return false;
//    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.d("TTW", "onDraw();");
//        Log.d
//        canvas.drawColor(Color.CYAN);

//        paint.setAntiAlias(true);
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(1f);
//        paint.setAlpha(0x80);
//        int a = 10;
//        canvas.drawRect(getWidth()/2-a, getHeight()/2-a, getWidth()/2+a, getHeight()/2+a, paint);

        drawGrid(canvas);
        drawRelief(canvas);

//        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
//        canvas.drawLine(getWidth(), 0, 0, getHeight(), paint);
    }

    public void drawGrid(Canvas canvas) {
        int mainCoorMapX = field.getMainCoorMapX();
        int mainCoorMapY = field.getMainCoorMapY();
        int spaceWidget = field.getSpaceWidget();
        int sizeCell = field.getSizeCell();

        int fieldX = field.getSizeX();
        int fieldY = field.getSizeY();

//        p.setPen(QColor(100,60,21));
//        paint.setARGB(255, 0, 255, 0);
        paint.setARGB(255,100,60,21);
        paint.setStrokeWidth(1f);

        for(int k = 0; k < fieldX+1; k++)
            canvas.drawLine(mainCoorMapX + spaceWidget + k*sizeCell, mainCoorMapY + spaceWidget, mainCoorMapX + spaceWidget + k*sizeCell, mainCoorMapY + spaceWidget + sizeCell*fieldY, paint);

        for(int k = 0; k < fieldY+1; k++)
            canvas.drawLine(mainCoorMapX + spaceWidget, mainCoorMapY + spaceWidget + k*sizeCell, mainCoorMapX + spaceWidget + sizeCell*fieldX, mainCoorMapY + spaceWidget + k*sizeCell, paint);
    }

    public void drawRelief(Canvas canvas) {
//        paint.setARGB(255,100,60,21);
        paint.setColor(Color.BLACK);

        int fieldX = field.getSizeX();
        int fieldY = field.getSizeY();

        for(int y = 0; y < fieldY; y++)
        {
            for(int x = 0; x < fieldX; x++)
            {
                int mainCoorMapX = field.getMainCoorMapX();
                int mainCoorMapY = field.getMainCoorMapY();
                int spaceWidget = field.getSpaceWidget();
                int sizeCell = field.getSizeCell();

                float pxlsX = mainCoorMapX + spaceWidget + x*sizeCell;//+1;
                float pxlsY = mainCoorMapY + spaceWidget + y*sizeCell;//+1;
                float space = 0; //sizeCell/5;

                if(field.containBusy(x, y))
                {
//                if(!mapLoad)
                    canvas.drawRect(pxlsX+1 + space, pxlsY+1 + space, pxlsX + sizeCell - space, pxlsY + sizeCell - space, paint);//QColor(0, 0, 0));
//                    p.fillRect(pxlsX+1, pxlsY+1, localSizeCell-1, localSizeCell-1, QColor(0, 0, 0));
//                else
//                    p.drawPixmap(pxlsX, pxlsY, localSizeCell, localSizeCell, field.getBusyPixmapOfCell(x, y));
                }
            }
        }
    }
}
