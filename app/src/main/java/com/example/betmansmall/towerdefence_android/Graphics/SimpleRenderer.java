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
    FloatBuffer floatBuffer;
    private final int tdId = R.drawable.td_001;
    private int glIdTd ;
    private boolean isDrawMenu = false;
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
        if(isDrawMenu) {
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            glIdTd = loadTexture(gl, tdId);
        }
        floatBuffer = createAndFillFloat(new float[]{
                -1.0f, -1.0f, 0.0f,
                0.0f, 0.0f , 0.0f,
                -1.0f, 1.0f, 0.0f,
                0.0f, 0.0f , 0.0f,
                1.0f, 1.0f, 0.0f,
                0.0f, 0.0f , 0.0f,
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

    @Override
    public void onDrawFrame(GL10 gl) {
        if(isDrawMenu) {
            FloatBuffer tpoints;
            tpoints = createAndFillFloat(new float[]{
                    0.0f, 0.0f,
                    1.0f, 0.0f,
                    1.0f, 1.0f,
                    0.0f, 1.0f
            });
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, glIdTd);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, tpoints);
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
            return;
        }

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); //Очистка буфера цвета и глубины
        gl.glMatrixMode(GL10.GL_PROJECTION); //для 2D плоскости грубо говоря
        gl.glLoadIdentity();

        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, floatBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        ShortBuffer indexes = createAndFillShort(new short[]{0,1,2,3,4,5,6,7});
        gl.glDrawElements(GL10.GL_LINES, 8, GL10.GL_UNSIGNED_SHORT, indexes);

    }

    public void onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                createNewFloatBuffer(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                floatBuffer = createAndFillFloat(new float[]{
                        -1.0f, -1.0f, 0.0f,
                        0.0f, 0.0f , 0.0f,
                        -1.0f, 1.0f, 0.0f,
                        0.0f, 0.0f , 0.0f,
                        1.0f, 1.0f, 0.0f,
                        0.0f, 0.0f , 0.0f,
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
        floatBuffer = createAndFillFloat(new float[]{
                -1.0f, -1.0f, 0.0f,
                newWidth, newHeight , 0.0f,
                -1.0f, 1.0f, 0.0f,
                newWidth, newHeight , 0.0f,
                1.0f, 1.0f, 0.0f,
                newWidth, newHeight , 0.0f,
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
