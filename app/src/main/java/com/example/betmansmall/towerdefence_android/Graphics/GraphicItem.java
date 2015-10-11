package com.example.betmansmall.towerdefence_android.Graphics;

import android.support.annotation.Size;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Дима Цыкунов on 10.10.2015.
 * This class include Short&Float buffers for drawing,
 * float(-1f..1f) coordinates screen and normal(px) coordinates screen
 */
public class GraphicItem {
    public static final int SHORT_FIELD_SIZE    = 2;
    public static final int FLOAT_FIELD_SIZE    = 4;
    protected FloatBuffer floatBufferOfPicture; //buffer of capture image
    protected FloatBuffer floatBufferOfPosition; //buffer of position image on screen
    protected ShortBuffer shortBufferOfIndexes; //buffer of index position

    private boolean isDrawPicture = false;

    protected GameConstants gameConstants = GameConstants.getInstance();

    private int pxYCoordinateScreenDown;
    private int pxXCoordianteScreenDown;
    private int pxYCoordinateScreenUp;
    private int pxXCoordianteScreenUp;
    private float fYCoordinateScreenDown;
    private float fXCoordianteScreenDown;
    private float fYCoordinateScreenUp;
    private float fXCoordianteScreenUp;

    //intYDown + intXDown - left down corner of picture
    //intYUp + intYUp - right up corner
    GraphicItem(final int intXDown, final int intYDown,
    final int intXUp, final int intYUp) {
        this.pxYCoordinateScreenDown = intYDown;
        this.pxYCoordinateScreenUp = intYUp;
        this.pxXCoordianteScreenDown = intXDown;
        this.pxXCoordianteScreenUp = intXUp;
        this.fYCoordinateScreenDown = heightToFloat(intYDown);
        this.fYCoordinateScreenUp = heightToFloat(intYUp);
        this.fXCoordianteScreenDown = widthToFloat(intXDown);
        this.fXCoordianteScreenUp = widthToFloat(intXUp);
    }

    //TODO Should be overrided in subclass or make it as design
    public void drawGraphicItem(GL10 _gl) {
        return;
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

    public void initFloatBufferOfPosition(float[] floatArray) {
        if(floatArray == null)
            floatBufferOfPosition = createAndFillFloat(new float[]{
                    // x,y,z
                    // so, default view point (contsructor param).
                    fXCoordianteScreenDown, fYCoordinateScreenDown, 0.0f,
                    fXCoordianteScreenDown, fYCoordinateScreenUp, 0.0f,
                    fXCoordianteScreenUp, fYCoordinateScreenUp, 0.0f,
                    fXCoordianteScreenUp, fYCoordinateScreenDown, 0.0f
            });
        else
            floatBufferOfPosition = createAndFillFloat(
                    floatArray
            );
    }

    public void putFloatBufferOfPosition(int[] indexArray, float[] pointArray) {
        for(int i = 0; i < indexArray.length; i++) {
            floatBufferOfPosition.put(indexArray[i],pointArray[i]);
        }
    }

    public FloatBuffer getFloatBufferOfPosition() {
        return floatBufferOfPosition;
    }

    public void initShortBufferOfIndexes(short[] shortArray) {
        if(shortArray == null)
            shortBufferOfIndexes = createAndFillShort(new short[]{
                    //default rectangles consisting of two triangles
                    0, 1, 2, 0, 2, 3
            });
        else
            shortBufferOfIndexes = createAndFillShort(
                    shortArray
            );
    }

    public void putFloatBufferOfIndexes(int[] indexArray, short[] pointArray) {
        for (int i = 0; i < indexArray.length; i++) {
            shortBufferOfIndexes.put(indexArray[i],pointArray[i]);
        }
    }

    public ShortBuffer getShortBufferOfIndexes() {
        return shortBufferOfIndexes;
    }

    private final ShortBuffer createAndFillShort(short[] _massive){
        int len = _massive.length;
        ByteBuffer bb = ByteBuffer.allocateDirect(len * SHORT_FIELD_SIZE);
        bb.order(ByteOrder.nativeOrder());
        ShortBuffer result = bb.asShortBuffer();
        result.put(_massive);
        result.position(0);
        return result;
    }

    private final FloatBuffer createAndFillFloat(float[] _massive) {
        int len = _massive.length;
        ByteBuffer bb = ByteBuffer.allocateDirect(len * FLOAT_FIELD_SIZE);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer result = bb.asFloatBuffer();
        result.put(_massive);
        result.position(0);
        return result;
    }


    public boolean isDrawPicture() { return isDrawPicture; }
    public void setIsDrawPicture(boolean isDrawPicture) { this.isDrawPicture = isDrawPicture; }

    private int widthToInt(float width) { return (int)((1 + width)*gameConstants.getWindowWidth() / 2);}
    private int heightToInt(float height) { return (int)((1 - height)*gameConstants.getWindowHeight() / 2);}
    private float widthToFloat(float width) { return width*2/ gameConstants.getWindowWidth() - 1;}
    private float heightToFloat(float height) { return height*2/ gameConstants.getWindowHeight() - 1;}
}
