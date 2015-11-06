package com.example.betmansmall.towerdefence_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();

    public Field field = new Field();
    private int gameCoordXForWhichCell;
    private int gameCoordYForWhichCell;

    public DrawView(Context context) {
        super(context);
        init(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    public void init(Context context) {
        paint.setColor(Color.BLACK);

//        Log.d("TTW", "getWidth(): " + getWidth());
//        Log.d("TTW", "getHeight(): " + getHeight());

        int sizeCell = 64;
//        int sizeX = (int) (getWidth()/sizeCell); // 25; // 65, 120
//        int sizeY = (int) (getHeight()/sizeCell); // 11; // 30, 60
        int sizeX = 28; // 65, 120
        int sizeY = 14; // 30, 60

        field = new Field();
        field.createField(sizeX, sizeY); // 65, 30
        field.setSizeCell(sizeCell);

        Log.d("TTW", "field.sizeX(): " + field.getSizeX());
        Log.d("TTW", "field.sizeY(): " + field.getSizeY());

        for(int y = 0; y < field.getSizeY(); y++)
            for(int x = 0; x < field.getSizeX(); x++)
                if(Math.random()*101 <= 30)
                    field.setBusy(x, y);

//        for(int x = field.getSizeX(); x >= 0; x--)
//            for(int y = field.getSizeY(); y >= 0; y--)
//                if(rand()%101 <= 10)
//                    field.setTower(x, y);

        int defaultNumCreateCreeps = 10;
//        int numCreepsK = 0;
//        for(int y = 0; y < field.getSizeY(); y++)
//            for(int x = 0; x < field.getSizeX(); x++)
//                if(Math.random()*101 <= 20)
//                    if(numCreepsK++ < defaultNumCreateCreeps)
//                        field.setCreep(x, y);

        field.setBusy(0, 0);
        field.setBusy(1, 0);
        field.setBusy(1, 1);
        field.setBusy(0, 1);

        field.createSpawnPoint(defaultNumCreateCreeps, 0, 0);
        field.createExitPoint(field.getSizeX() - 1, field.getSizeY() - 1);

        field.setCreep(0, 0);
        field.setCreep(0, 0);
        field.setCreep(0, 0);

//        loadMap(TOWER_DEFENCE_PATH + "maps/arcticv1.tmx");
    }

    public void mousePressEvent(float mouseX, float mouseY) {
        gameCoordXForWhichCell = new Integer( (int) mouseX);
        gameCoordYForWhichCell = new Integer( (int) mouseY);

        if(whichCell(gameCoordXForWhichCell, gameCoordYForWhichCell)) {
            Log.d("TTW", "mousePressEvent() -- mouseX: " + gameCoordXForWhichCell + " mouseY: " + gameCoordYForWhichCell);
            if(MainActivity.inputMode) {
                field.createExitPoint(gameCoordXForWhichCell, gameCoordYForWhichCell);
//                field.waveAlgorithm(gameCoordXForWhichCell, gameCoordYForWhichCell);
            } else {
                if (field.containEmpty(gameCoordXForWhichCell, gameCoordYForWhichCell)) {
                    field.setBusy(gameCoordXForWhichCell, gameCoordYForWhichCell);
                } else if (field.containBusy(gameCoordXForWhichCell, gameCoordYForWhichCell)) {
                    field.clearBusy(gameCoordXForWhichCell, gameCoordYForWhichCell);
                }

                field.waveAlgorithm();
            }
            invalidate();
        }
    }

    public boolean whichCell(Integer mouseX, Integer mouseY) {
        int mainCoorMapX = field.getMainCoorMapX();
        int mainCoorMapY = field.getMainCoorMapY();
        int spaceWidget = field.getSpaceWidget();
        int sizeCell = field.getSizeCell();

        int tmpX, tmpY;
        tmpX = ( (mouseX.intValue()+sizeCell - spaceWidget - mainCoorMapX) / sizeCell);
        tmpY = ( (mouseY.intValue()+sizeCell - spaceWidget - mainCoorMapY) / sizeCell);
        if(tmpX > 0 && tmpX < field.getSizeX()+1)
            if(tmpY > 0 && tmpY < field.getSizeY()+1)
            {
                gameCoordXForWhichCell = tmpX-1;
                gameCoordYForWhichCell = tmpY-1;
                return true;
            }

        return false;
    }

    public void repaint() {
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.d("TTW", "onDraw();");
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
        drawCreeps(canvas);
        drawStepsAndMouse(canvas);

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
        paint.setARGB(255, 100, 60, 21);
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
                float localSizeCell = sizeCell;//-1;
                float localSpaceCell = 0;

                if(field.containBusy(x, y))
                {
//                if(!mapLoad)
                    canvas.drawRect(pxlsX+1 + localSpaceCell, pxlsY+1 + localSpaceCell, pxlsX + sizeCell - localSpaceCell, pxlsY + sizeCell - localSpaceCell, paint);//QColor(0, 0, 0));
//                    p.fillRect(pxlsX+1, pxlsY+1, localSizeCell-1, localSizeCell-1, QColor(0, 0, 0));
//                else
//                    p.drawPixmap(pxlsX, pxlsY, localSizeCell, localSizeCell, field.getBusyPixmapOfCell(x, y));
                }
            }
        }
    }

    public void drawCreeps(Canvas canvas) {
//        Log.d("TTW", "drawCreeps(1);");
        int mainCoorMapX = field.getMainCoorMapX();
        int mainCoorMapY = field.getMainCoorMapY();
        int spaceWidget = field.getSpaceWidget();
        int sizeCell = field.getSizeCell();

        int fieldX = field.getSizeX();
        int fieldY = field.getSizeY();

        for(int y = 0; y < fieldY; y++) {
//            Log.d("TTW", "drawCreeps(2);");
            for(int x = 0; x < fieldX; x++) {
//                Log.d("TTW", "drawCreeps(3);");
//                int num = field.containCreep(x, y);
                if(field.containCreep(x, y)) {
//                    Log.d("TTW", "drawCreeps(4);");
                    float pxlsX = mainCoorMapX + spaceWidget + x*sizeCell;//+1;
                    float pxlsY = mainCoorMapY + spaceWidget + y*sizeCell;// - sizeCell/2;//+1;
                    float localSizeCell = sizeCell;//-1;
                    float localSpaceCell = sizeCell/5;

//                QColor color = QColor(num*10, num*10, num*10);
//                p.fillRect(pxlsX+1 + localSpaceCell, pxlsY+1 + localSpaceCell, localSizeCell-1 - 2*(localSpaceCell), localSizeCell-1 - 2*(localSpaceCell), color);

                    paint.setColor(Color.RED);
//                    canvas.drawRect(pxlsX+1 + localSpaceCell, pxlsY+1 + localSpaceCell, pxlsX + localSizeCell - localSpaceCell, pxlsY + localSizeCell - localSpaceCell, paint);
//                    canvas.drawRect(pxlsX+1 + space, pxlsY+1 + space, pxlsX + sizeCell - space, pxlsY + sizeCell - space, paint);//QColor(0, 0, 0));
//                    canvas.drawLine(pxlsX, pxlsY, pxlsX + localSizeCell, pxlsY + localSizeCell, paint);
//                    canvas.drawLine(pxlsX + localSizeCell, pxlsY, pxlsX, pxlsY + localSizeCell, paint);
                    canvas.drawCircle(pxlsX + localSizeCell/2, pxlsY + localSizeCell/2, localSpaceCell, paint);

//                    std::vector<Creep*> creeps = field.getCreeps(x, y);
//                    int size = creeps.size();
//                    for(int k = 0; k < size; k++)
//                    {
//                        if(creeps[k]->alive || creeps[k]->preDeath) // fixed!!!
//                        {
//                            int lastX, lastY;
//                            int animationCurrIter, animationMaxIter;
//                            QPixmap pixmap = creeps[k]->getAnimationInformation(&lastX, &lastY, &animationCurrIter, &animationMaxIter);
//
//                            pxlsX = mainCoorMapX + spaceWidget + x*sizeCell - localSpaceCell;
//                            pxlsY = mainCoorMapY + spaceWidget + y*sizeCell - localSpaceCell;
//
//                            if(lastX < x)
//                                pxlsX -= (sizeCell/animationMaxIter)*(animationMaxIter-animationCurrIter);
//                            if(lastX > x)
//                                pxlsX += (sizeCell/animationMaxIter)*(animationMaxIter-animationCurrIter);
//                            if(lastY < y)
//                                pxlsY -= (sizeCell/animationMaxIter)*(animationMaxIter-animationCurrIter);
//                            if(lastY > y)
//                                pxlsY += (sizeCell/animationMaxIter)*(animationMaxIter-animationCurrIter);
//
//                            p.drawPixmap(pxlsX, pxlsY, localSizeCell + localSpaceCell*2, localSizeCell + localSpaceCell*2, pixmap);
//                            //                    p.drawRect(pxlsX, pxlsY, localSizeCell + localSpaceCell*2, localSizeCell + localSpaceCell*2);
//
//                            int maxHP = 100;
//                            int hp = creeps[k]->hp;
//                            float hpWidth = localSizeCell-5;
//                            hpWidth = hpWidth/maxHP*hp;
////                        qDebug() << "hpWidth: " << hpWidth;
//
//                            p.drawRect(pxlsX + localSpaceCell+2, pxlsY, localSizeCell-4, 10);
//                            p.fillRect(pxlsX + localSpaceCell+3, pxlsY+1, hpWidth, 9, QColor(Qt::green));
//
//                            // IT's NOT GOOD!!! Fixed!
//                            creeps[k]->coorByMapX = pxlsX;
//                            creeps[k]->coorByMapY = pxlsY;
//                            // -----------------------
//                        }
//                    }
                }
            }
        }
    }

    void drawStepsAndMouse(Canvas canvas) {
        int mainCoorMapX = field.getMainCoorMapX();
        int mainCoorMapY = field.getMainCoorMapY();
        int spaceWidget = field.getSpaceWidget();
        int sizeCell = field.getSizeCell();

//        p.setPen(QColor(255,0,0));
//        paint.setARGB(255, 255, 0, 0);
        paint.setColor(Color.RED);

        int fieldX = field.getSizeX();
        int fieldY = field.getSizeY();

        for(int y = 0; y < fieldY; y++)
        {
            for(int x = 0; x < fieldX; x++)
            {
                int pxlsX = mainCoorMapX + spaceWidget + x*sizeCell+1;
                int pxlsY = mainCoorMapY + spaceWidget + y*sizeCell+1;
                int localSizeCell = sizeCell-1;
                int localSpaceCell = sizeCell/4;

//                p.drawPixmap(sizeCell, 0, global_pixmap.width(), global_pixmap.height(), global_pixmap);

                if(field.getStepCell(x, y) != 0) {
//                    p.drawText(pxlsX + sizeCell / 2 - 5, pxlsY + sizeCell / 2 + 5, QString("%1").arg(field.getStepCell(x, y)));
                    String str = String.valueOf(field.getStepCell(x, y));
                    canvas.drawText(str, pxlsX + sizeCell / 2 - 5, pxlsY + sizeCell / 2 + 5, paint);
                }

//                if(field.isSetSpawnPoint(x,y)) {
//                    p.fillRect(pxlsX + localSpaceCell, pxlsY + localSpaceCell, localSizeCell - 2 * (localSpaceCell), localSizeCell - 2 * (localSpaceCell), QColor(255, 162, 0));
//                }
//
//                if(field.isSetExitPoint(x, y)) {
//                    p.fillRect(pxlsX + localSpaceCell, pxlsY + localSpaceCell, localSizeCell - 2 * (localSpaceCell), localSizeCell - 2 * (localSpaceCell), QColor(0, 255, 0));
//                }
            }
        }
    }
}
