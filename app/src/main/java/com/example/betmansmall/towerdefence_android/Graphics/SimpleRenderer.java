package com.example.betmansmall.towerdefence_android.Graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.MotionEvent;

import com.example.betmansmall.towerdefence_android.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Дима Цыкунов on 03.10.2015.
 */
public class SimpleRenderer implements GLSurfaceView.Renderer {
    GameConstants gameConstants;

    FloatBuffer spiderBuffer;
    FloatBuffer bgScreenBuffer;
    FloatBuffer animBuffer;
    FloatBuffer spriteBuffer;

    private final int td_001 = R.drawable.td_001;
    private final int sprite2 = R.drawable.sprite2;
    private final int anim1 = R.drawable.anim1;

    private int glIdTD_001;
    private int glIdSprite2;
    private int glIdAnim1;

    private boolean isDrawMenu = true;
    private boolean isDrawAnim = true;
    private boolean isDrawSprite = true;
    private boolean isDrawSpider = false;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gameConstants = GameConstants.getInstance();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);//Задается цвет в режиме RGBA от 0.0 до 1.0

        gl.glViewport(0, 0, gameConstants.getWindowWidth(), gameConstants.getWindowHeight());
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, gameConstants.getWindowWidth(), gameConstants.getWindowHeight(), 0, -1, 1);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        Log.d("GLSurfaceViewTest", "surface created");

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        glIdTD_001 = loadTexture(gl, td_001);
        glIdAnim1 = loadTexture(gl,anim1);
        glIdSprite2 = loadTexture(gl,sprite2);

        spiderBuffer = createAndFillFloat(new float[]{
                -1.0f, -1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                0.0f, 0.0f , 0.0f
        });
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glLoadIdentity();
        Log.d("GLSurfaceViewTest", "surface changed: " + width + "x"
                + height);
    }

    private long loopStart =0;
    private long loopEnd = 0;
    private long loopRunTime = 0;
    private final int FPS = (1000/30);

    private int frameCount = 0;
    private int yAnim = 0;
    private int xAnim = 0;
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
        if(isDrawMenu) {
            drawMainMenu(gl);
        }
        if(isDrawAnim) {
            drawAnim(gl);
        }
        if(isDrawSprite) {
            drawSprite(gl);
        }

        if(isDrawSpider) {
            drawSpired(gl);
        }

    }

    private void drawSpired(GL10 gl) {

        gl.glColor4f(87 / 100, 67 / 100, 93 / 100, 1);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, spiderBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        ShortBuffer indexes = createAndFillShort(new short[]{0,4,1,4,2,4,3,4});
        gl.glDrawElements(GL10.GL_LINES, 8, GL10.GL_UNSIGNED_SHORT, indexes);
    }

    private void drawMainMenu(GL10 gl) {
        bgScreenBuffer = createAndFillFloat(new float[]{
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        });
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, glIdTD_001);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, bgScreenBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, createAndFillFloat(new float[]{
                -1.0f, -1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 0.0f
        }));
        gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, createAndFillShort(new short[]{
                0, 1, 2, 0, 2, 3
        }));
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    private void drawSprite(GL10 gl) {
        spriteBuffer = createAndFillFloat(new float[]{
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        });
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, glIdSprite2);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, spriteBuffer);

        FloatBuffer newBuf = createAndFillFloat(new float[]{
                (0f - 1f), -1.0f, 0.0f,
                (0f - 1f), -0.75f, 0.0f,
                (0.1f - 1f), -0.75f, 0.0f,
                (0.1f - 1f), -1.0f, 0.0f
        });
        ShortBuffer newSBuf = createAndFillShort(new short[]{
                0, 1, 2, 0, 2, 3
        });
        for(int i = 0;i<10;i++) {
            newBuf.put(0,i*2.0f/10.0f - 1f);
            newBuf.put(3,i*2.0f/10.0f - 1f);
            newBuf.put(6,((i+1.0f)*2.0f/10.0f - 1f));
            newBuf.put(9,((i+1.0f)*2.0f/10.0f - 1f));
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, newBuf);
            gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, newSBuf);
        }
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    private void drawAnim(GL10 gl) {
        animBuffer = createAndFillFloat(new float[]{
                //x,y
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        });
        animBuffer.put(0,0.0f);
        animBuffer.put(1,1f-3f/4f);
        animBuffer.put(2,0.0f);
        animBuffer.put(3,0.0f);
        animBuffer.put(4,1f-6f/7f);
        animBuffer.put(5,0.0f);
        animBuffer.put(6, 1f - 6f / 7f);
        animBuffer.put(7, 1f - 3f / 4f);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, glIdAnim1);
        FloatBuffer newFloat = createAndFillFloat(new float[]{
                -0.25f, -0.75f, 0.0f,
                -0.25f, 0.25f, 0.0f,
                0.25f, 0.25f, 0.0f,
                0.25f, -0.75f, 0.0f
        });

        ShortBuffer newShort = createAndFillShort(new short[]{
                0, 1, 2, 0, 2, 3
        });

        animBuffer.put(0, 1f - (7f- xAnim)/7f);
        animBuffer.put(1, 1f - (3f- yAnim)/4f);
        animBuffer.put(2, 1f - (7f- xAnim)/7f);
        animBuffer.put(3, 1f - (4f- yAnim)/4f);
        animBuffer.put(4, 1f - (6f- xAnim)/7f);
        animBuffer.put(5, 1f - (4f- yAnim)/4f);
        animBuffer.put(6, 1f - (6f - xAnim) / 7f);
        animBuffer.put(7, 1f - (3f - yAnim) / 4f);
        gl.glColor4f(0f,0f,0f,1f);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, animBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, newFloat);
        gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, newShort);

        if(frameCount > 2) { //Draw each second frame
            frameCount = 0;
            xAnim++;
            if (xAnim == 6 && yAnim == 3) {
                xAnim = 0;
                yAnim = 0;
            } else if (xAnim == 7) {
                yAnim++;
                xAnim = 0;
            }
        }
        frameCount++;

        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    public void onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                createNewFloatBuffer(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                spiderBuffer = createAndFillFloat(new float[]{
                        -1.0f, -1.0f, 0.0f,
                        -1.0f, 1.0f, 0.0f,
                        1.0f, 1.0f, 0.0f,
                        1.0f, -1.0f, 0.0f,
                        0.0f, 0.0f , 0.0f
                });
                break;
            case MotionEvent.ACTION_MOVE:
                createNewFloatBuffer(event.getX(),event.getY());
                break;
        }
        Log.d("GLSurfaceViewTest", "Touch event fired: " + event.toString());

    }

    float widthToFloat(float width) {
        return width*2/ gameConstants.getWindowWidth() - 1;
    }

    float heightToFloat(float height) {
        return -(height*2/ gameConstants.getWindowHeight() - 1);
    }

    void createNewFloatBuffer(float eventWidth,float eventHeight) {
        float newWidth = widthToFloat(eventWidth);
        float newHeight = heightToFloat(eventHeight);
        spiderBuffer = createAndFillFloat(new float[]{
                -1.0f, -1.0f, 0.0f,
                -1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                newWidth, newHeight , 0.0f,
        });
    }

    public int loadTexture(GL10 _gl, int _id){
        int[] a = new int[1];
        _gl.glGenTextures(1, a, 0);// я так понимаю что 1 текстуру начиная с нуля
        int tid = a[0];
        Log.i("TEX", "S1 "+tid);
        _gl.glBindTexture(GL10.GL_TEXTURE_2D, tid);
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        _gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);
        Bitmap bit;
        InputStream is = gameConstants.getmContext().getResources().openRawResource(_id);
        try{bit = BitmapFactory.decodeStream(is); }finally{
            try {
                is.close();
            } catch (IOException ex) {
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bit, 0);
        bit.recycle();
        return tid;
    }

    public final ShortBuffer createAndFillShort(short[] _massive){
        int len = _massive.length;
        ByteBuffer bb = ByteBuffer.allocateDirect(len * 2);
        bb.order(ByteOrder.nativeOrder());
        ShortBuffer result = bb.asShortBuffer();
        result.put(_massive);
        result.position(0);
        return result;
    }

    public final FloatBuffer createAndFillFloat(float[] _massive) {
        int len = _massive.length;
        ByteBuffer bb = ByteBuffer.allocateDirect(len * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer result = bb.asFloatBuffer();
        result.put(_massive);
        result.position(0);
        return result;
    }

}
