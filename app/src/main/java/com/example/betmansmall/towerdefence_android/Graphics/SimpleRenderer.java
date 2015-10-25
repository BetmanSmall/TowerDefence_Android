package com.example.betmansmall.towerdefence_android.Graphics;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.example.betmansmall.towerdefence_android.R;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Дима Цыкунов on 03.10.2015.
 */
public class SimpleRenderer implements GLSurfaceView.Renderer {
    GameConstants gameConstants;

    private boolean isSwtichActived = false;
    private long loopStart =0;
    private long loopEnd = 0;
    private long loopRunTime = 0;
    private final int FPS = (1000/30);

    private int yAnim = 0;
    private int xAnim = 0;

    GraphicItem grid;

    PictureGraphicItem backgroundPicture;
    PictureGraphicItem spritePicture;
    PictureGraphicItem animPicture;
    AnimationGraphicItem mainMenu;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gameConstants = GameConstants.getInstance();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);//Задается цвет в режиме RGBA от 0.0 до 1.0

        gl.glViewport(0, 0, gameConstants.getWindowWidth(), gameConstants.getWindowHeight());
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, gameConstants.getWindowWidth(), gameConstants.getWindowHeight(), 0, -1, 1);
        gl.glDisable(GL10.GL_DEPTH_TEST);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        firstInitialization(gl);

    }

    //three way to create picture.
    private void firstInitialization(GL10 _gl) {
        backgroundPicture = new PictureGraphicItem(0,0,gameConstants.getWindowWidth(),gameConstants.getWindowHeight());
        backgroundPicture.initFloatBufferOfPosition(null); //use constructor value
        backgroundPicture.initFloatBufferOfPicture(null); //use default value
        backgroundPicture.initShortBufferOfIndexes(null); //use default value
        backgroundPicture.setRecourseImage(R.drawable.td_001); //set picture
        backgroundPicture.setIsDrawPicture(true); //make visible
        backgroundPicture.loadTextureToGl(_gl); //load texture

        spritePicture = new PictureGraphicItem(0,0,gameConstants.getWindowWidth()/10,gameConstants.getWindowHeight()/7);
        spritePicture.lazyDefaultInitialization(_gl,R.drawable.sprite2); //all default

        animPicture = new PictureGraphicItem(0,0,0,0);
        animPicture.initFloatBufferOfPosition(new float[]{
                -0.25f, -0.75f, 0.0f,
                -0.25f, 0.25f, 0.0f,
                0.25f, 0.25f, 0.0f,
                0.25f, -0.75f, 0.0f
        });
        animPicture.initFloatBufferOfPicture(null); // on firs step - no need. In drawMethod change this value
        animPicture.initShortBufferOfIndexes(null);
        //animPicture.setIsDrawPicture(true);
        animPicture.setRecourseImage(R.drawable.anim1); // setRecourseImage should be execute before loadTextureToGl!
        animPicture.loadTextureToGl(_gl);

        grid = new GraphicItem();
        grid.initFloatBufferOfPosition(new float[]{
                0f, 0f, 0f,
                1f, 1f, 0f,
                -1f, 1f, 0f,
                0f, 0f, 0f,
        });
        grid.initShortBufferOfIndexes(new short[]{0, 1, 2, 3});
        //grid.setIsDrawPicture(true);
        grid.sizeShortBuf = 4;

        //TODO test view should be changed
        mainMenu = new AnimationGraphicItem(gameConstants.widthToInt(-0.18f),gameConstants.heightToInt(-1f),
                gameConstants.widthToInt(0.18f),gameConstants.heightToInt(0.14f));
        mainMenu.lazyDefaultInitialization(_gl,R.drawable.mainmennu);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glLoadIdentity();
        Log.d("GLSurfaceViewTest", "surface changed: " + width + "x"
                + height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        loopStart = System.currentTimeMillis();
        if(loopRunTime < FPS )
        {
            try{
                Thread.sleep(FPS - loopRunTime);
            }catch(Exception e) {Log.d("Error","Exc: "+ e);}
        }
        loopEnd = System.currentTimeMillis();
        loopRunTime = ((loopEnd - loopStart));

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); //Очистка буфера цвета и глубины
        gl.glMatrixMode(GL10.GL_PROJECTION); //для 2D плоскости грубо говоря
        gl.glLoadIdentity();
        gl.glEnable(GL10.GL_TEXTURE_2D);

        if(backgroundPicture.isDrawPicture()) {
            backgroundPicture.drawGraphicItem(gl);
        }

        if(spritePicture.isDrawPicture()) {
            for(float i = 0;i<10;i++) {
                spritePicture.putFloatBufferOfPosition(new int[]{0,3,6,9},new float[]{
                        i*2.0f/10.0f - 1f,
                        i*2.0f/10.0f - 1f,
                        ((i+1.0f)*2.0f/10.0f - 1f),
                        ((i+1.0f)*2.0f/10.0f - 1f)
                });
                spritePicture.drawGraphicItem(gl);
            }
        }

        if(animPicture.isDrawPicture()) {
            //add specific parametrs for transaprent image
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            animPicture.putFloatBufferOfPicture(new int[] {0,1,2,3,4,5,6,7},new float[] {
                    1f - (7f - xAnim) / 7f,
                    1f - (3f - yAnim) / 4f,
                    1f - (7f - xAnim) / 7f,
                    1f - (4f - yAnim) / 4f,
                    1f - (6f - xAnim) / 7f,
                    1f - (4f - yAnim) / 4f,
                    1f - (6f - xAnim) / 7f,
                    1f - (3f - yAnim) / 4f
            });
            animPicture.drawGraphicItem(gl);
            gl.glDisable(GL10.GL_BLEND);

            //TODO should be replaced in AnimationGraphicItem
            xAnim++;
            if (xAnim == 6 && yAnim == 3) {
                xAnim = 0;
                yAnim = 0;
            } else if (xAnim == 7) {
                yAnim++;
                xAnim = 0;
            }
        }

        if (grid.isDrawPicture())
        {
            grid.drawLines(gl);
        }

        if (mainMenu.isDrawPicture())
        {
            //add specific parametrs for transaprent image
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            mainMenu.drawGraphicItem(gl);
            gl.glDisable(GL10.GL_BLEND);
        }

        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    private void switchMenu(final AnimationGraphicItem _old,final AnimationGraphicItem _new) {
        // TODO: 25.10.2015 Should be redesigned in future
        if(!isSwtichActived) {
            isSwtichActived = true;
            _old.setStatus(AnimationGraphicItem.Status.MENU_SLIDE_DOWN);
            new Thread() {
                AnimationGraphicItem __old = _old;
                AnimationGraphicItem __new = _new;

                @Override
                public void run() {
                    while (__old.getStatus() != AnimationGraphicItem.Status.MENU_NORMAL)
                        ;
                    __old.setIsDrawPicture(false);
                    __new.setLowerPositionOfMenu(__old.getFloatBufferOfPosition());
                    __new.setIsDrawPicture(true);
                    __new.setStatus(AnimationGraphicItem.Status.MENU_SLIDE_UP);
                    while (__new.getStatus() != AnimationGraphicItem.Status.MENU_NORMAL)
                        ;
                    isSwtichActived = false;
                }
            }.start();
        }
    }

    public void onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                //TODO need to be checked what user do
                switchMenu(mainMenu,mainMenu);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        //Log.d("GLSurfaceViewTest", "Touch event fired: " + event.toString());

    }

}
