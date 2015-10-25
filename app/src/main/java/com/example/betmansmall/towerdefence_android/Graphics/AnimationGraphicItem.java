package com.example.betmansmall.towerdefence_android.Graphics;

import android.util.Log;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Дима Цыкунов on 10.10.2015.
 */
//Should be done
public class AnimationGraphicItem extends PictureGraphicItem {

    private final static float step = 0.02f;
    private final static float finalStateOfOffsetDown = -0.98f;
    private final static float finalStateOfOffsetUp = 0.14f;
    private int actionCount = 0;
    private final static int maxActionCount = 56; // (0.98+0.14)/0.04
    public enum Status {
        MENU_SLIDE_DOWN,
        MENU_SLIDE_UP,
        MENU_NORMAL
    }

    private Status animationStatus = Status.MENU_NORMAL;

    AnimationGraphicItem(int intXDown, int intYDown, int intXUp, int intYUp) {
        super(intXDown, intYDown, intXUp, intYUp);
    }

    public void setStatus(Status _status) {
        animationStatus = _status;
    }

    public Status getStatus(){
        return animationStatus;
    }

    public void setLowerPositionOfMenu(FloatBuffer _fb) {
        floatBufferOfPosition = _fb;
    }

    private void checkToMenuAnimationDone() {
        switch (animationStatus) {
            case MENU_SLIDE_DOWN:
            case MENU_SLIDE_UP:
                if(actionCount == maxActionCount) {
                    animationStatus = Status.MENU_NORMAL;
                    actionCount = -1;
                }
                break;
            case MENU_NORMAL:
                break;
            default:
                Log.d("TTW: Error","Unexpected type of animation status : " + animationStatus);
        }
    }

    @Override
    public void drawGraphicItem(GL10 _gl) {
        switch (animationStatus) {
            case MENU_SLIDE_DOWN:
                putFloatBufferOfPosition(new int[]{
                        1, 4, 7, 10}
                        , new float[]{
                        floatBufferOfPosition.get(1) - step,
                        floatBufferOfPosition.get(4) - step,
                        floatBufferOfPosition.get(7) - step,
                        floatBufferOfPosition.get(10) - step,
                });
                checkToMenuAnimationDone();
                actionCount++;
                break;
            case MENU_SLIDE_UP:
                putFloatBufferOfPosition(new int[]{
                        1, 4, 7, 10}
                        , new float[]{
                        floatBufferOfPosition.get(1) + step,
                        floatBufferOfPosition.get(4) + step,
                        floatBufferOfPosition.get(7) + step,
                        floatBufferOfPosition.get(10) + step,
                });
                checkToMenuAnimationDone();
                actionCount++;
                break;
            case MENU_NORMAL:
                break;
            default:
                Log.d("TTW: Error","Unexpected type of animation status : " + animationStatus);
        }
        super.drawGraphicItem(_gl);
    }
}
