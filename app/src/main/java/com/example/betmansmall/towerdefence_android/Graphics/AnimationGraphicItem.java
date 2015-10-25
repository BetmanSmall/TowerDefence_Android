package com.example.betmansmall.towerdefence_android.Graphics;

import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Дима Цыкунов on 10.10.2015.
 */
//Should be done
public class AnimationGraphicItem extends PictureGraphicItem {

    private static float step = 0.05f;
    private static float finalStateOfOffset = -0.95f;
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

    public void setLowerPositionOfMenu() {
        // TODO: 25.10.2015 costile
        //putFloatBufferOfPosition();
    }

    private void checkToMenuAnimationDone() {
        switch (animationStatus) {
            case MENU_SLIDE_DOWN:
                if(floatBufferOfPosition.get(4) <= finalStateOfOffset ) {
                    animationStatus = Status.MENU_NORMAL;
                }
                break;
            case MENU_SLIDE_UP:
                if(floatBufferOfPosition.get(4) >= 0.6f) { // TODO: 25.10.2015 Hardocded. Max height of menu
                    animationStatus = Status.MENU_NORMAL;
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
                break;
            case MENU_NORMAL:
                break;
            default:
                Log.d("TTW: Error","Unexpected type of animation status : " + animationStatus);
        }
        super.drawGraphicItem(_gl);
    }
}
