package com.example.betmansmall.towerdefence_android.Graphics;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Дима Цыкунов on 03.10.2015.
 */
public class GraphicsMenu extends Activity {
    GLSurfaceView glView;
    GameConstants gameConstants;
    SimpleRenderer simpleRenderer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        gameConstants = GameConstants.getInstance();
        gameConstants.setWindowHeight(height);
        gameConstants.setWindowWidth(width);
        gameConstants.setmContext(this);
        simpleRenderer = new SimpleRenderer();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glView = new GLSurfaceView(this);
        glView.setZOrderOnTop(true);
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        glView.setRenderer(simpleRenderer);
        //glView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(glView);
    }

    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        glView.onPause();
    }

    @Override
    // передаем onTouchEvent в поток Renderer
    public boolean onTouchEvent(final MotionEvent event) {
        glView.queueEvent(new Runnable() {
            public void run() {
                simpleRenderer.onTouchEvent(event);
            }
        });
        return true;
    }

}
