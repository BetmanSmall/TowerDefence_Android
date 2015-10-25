package com.example.betmansmall.towerdefence_android.Graphics;

import android.content.Context;

/**
 * Created by Дима Цыкунов on 03.10.2015.
 */
public class GameConstants {

    static GameConstants gcInstance = null;

    GameConstants() {
    }

    public static GameConstants getInstance() {
        synchronized (GameConstants.class) {
            if(gcInstance == null) {
                gcInstance = new GameConstants();
            }
            return gcInstance;
        }

    }

    private int windowWidth;
    private int windowHeight;
    private Context mContext;

    enum GameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    public int widthToInt(float width) { return (int)((1 + width)*getWindowWidth() / 2);}
    public int heightToInt(float height) { return (int)((1 + height)*getWindowHeight() / 2);}
    public float widthToFloat(float width) { return width*2/ getWindowWidth() - 1;}
    public float heightToFloat(float height) { return height*2/ getWindowHeight() - 1;}
}
