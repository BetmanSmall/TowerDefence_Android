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

    private long loopStart =0;
    private long loopEnd = 0;
    private long loopRunTime = 0;
    private final int FPS = (1000/30);

    private int yAnim = 0;
    private int xAnim = 0;

    PictureGraphicItem backgroundPicture;
    PictureGraphicItem spritePicture;
    PictureGraphicItem animPicture;
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
        animPicture.initFloatBufferOfPicture(null); // on firs step - no need. In drawMethood change this value
        animPicture.initShortBufferOfIndexes(null);
        animPicture.setIsDrawPicture(true);
        animPicture.setRecourseImage(R.drawable.anim1); // setRecourseImage should be execute before loadTextureToGl!
        animPicture.loadTextureToGl(_gl);
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
            /*newBuf.put(0,i*2.0f/10.0f - 1f);
            newBuf.put(3,i*2.0f/10.0f - 1f);
            newBuf.put(6,((i+1.0f)*2.0f/10.0f - 1f));
            newBuf.put(9,((i+1.0f)*2.0f/10.0f - 1f));previous ??? to follow ->*/
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

          /*animBuffer.put(0, 1f - (7f - xAnim) / 7f);
            animBuffer.put(1, 1f - (3f- yAnim)/4f);
            animBuffer.put(2, 1f - (7f- xAnim)/7f);
            animBuffer.put(3, 1f - (4f- yAnim)/4f);
            animBuffer.put(4, 1f - (6f- xAnim)/7f);
            animBuffer.put(5, 1f - (4f- yAnim)/4f);
            animBuffer.put(6, 1f - (6f - xAnim) / 7f);
            animBuffer.put(7, 1f - (3f - yAnim) / 4f);*/
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


        gl.glDisable(GL10.GL_TEXTURE_2D);
    }


    public void onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        Log.d("GLSurfaceViewTest", "Touch event fired: " + event.toString());

    }

}
