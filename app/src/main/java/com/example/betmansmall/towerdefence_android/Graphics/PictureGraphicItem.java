package com.example.betmansmall.towerdefence_android.Graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Дима Цыкунов on 10.10.2015.
 */
public class PictureGraphicItem extends GraphicItem {

    private int recourseImage = 0;
    private int glRecourseImage;

    protected FloatBuffer floatBufferOfPicture; //buffer of capture image

    PictureGraphicItem(int intXDown, int intYDown, int intXUp, int intYUp) {
        super(intXDown, intYDown, intXUp, intYUp);
    }

    //TODO can be parametrized. work as designer
    @Override
    public void drawGraphicItem(GL10 _gl) {

        _gl.glBindTexture(GL10.GL_TEXTURE_2D, glRecourseImage);
        _gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, floatBufferOfPicture);
        _gl.glVertexPointer(3, GL10.GL_FLOAT, 0, floatBufferOfPosition);
        _gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, shortBufferOfIndexes);

    }

    public void lazyDefaultInitialization(GL10 _gl,int drawableRecourse) {
        initFloatBufferOfPosition(null); //use constructor value
        initFloatBufferOfPicture(null); //use default value
        initShortBufferOfIndexes(null); //use default value
        setRecourseImage(drawableRecourse); //set picture
        setIsDrawPicture(true); //make visible
        loadTextureToGl(_gl); //load texture
    }

    public int getRecourseImage() {
        return recourseImage;
    }

    public void setRecourseImage(int recourseImage) {
        if(recourseImage != 0)
         this.recourseImage = recourseImage;
        else
            Log.d("Error","You cannot initialze recourse image twice");
    }

    public void initFloatBufferOfPicture(float[] floatArray) {
        if(floatArray == null)
            floatBufferOfPicture = createAndFillFloat(new float[]{
                    // x,y
                    // so, default view point (all picture).
                    // If sprite, should be redefine in putFloatBufferOfPicture()
                    0.0f, 1.0f,
                    0.0f, 0.0f,
                    1.0f, 0.0f,
                    1.0f, 1.0f,
            });
        else
            floatBufferOfPicture = createAndFillFloat(
                    floatArray
            );
    }

    public void putFloatBufferOfPicture(int[] indexArray, float[] pointArray) {
        for(int i = 0; i < indexArray.length; i++) {
            floatBufferOfPicture.put(indexArray[i],pointArray[i]);
        }
    }

    public FloatBuffer getFloatBufferOfPiture() {
        return floatBufferOfPicture;
    }

    public void loadTextureToGl(GL10 gl) {
        glRecourseImage = loadTexture(gl);
    }

    public int getLoadedTexture() {
        return glRecourseImage;
    }

    private int loadTexture(GL10 _gl){
        int[] a = new int[1];
        _gl.glGenTextures(1, a, 0);// я так понимаю что 1 текстуру начиная с нуля
        int tid = a[0];
        Log.i("Info", "Loading picture " + tid);
        _gl.glBindTexture(GL10.GL_TEXTURE_2D, tid);
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        _gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        _gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);
        Bitmap bit;
        InputStream is = gameConstants.getmContext().getResources().openRawResource(recourseImage);
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

}
