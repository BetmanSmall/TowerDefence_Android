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
    protected FloatBuffer floatBufferOfPosition; //buffer of position image on screen
    protected ShortBuffer shortBufferOfIndexes; //buffer of index position

    private boolean isDrawPicture = false;

    protected GameConstants gameConstants = GameConstants.getInstance();

    private int pxYCoordinateScreenDown;
    private int pxXCoordianteScreenDown;
    private int pxYCoordinateScreenUp;
    private int pxXCoordianteScreenUp;
    private int pxZCoordinateScreenUp;
    private int pxZCoordinateScreenDown;
    private float fYCoordinateScreenDown;
    private float fXCoordianteScreenDown;
    private float fYCoordinateScreenUp;
    private float fXCoordianteScreenUp;
    private float fZCoordinateScreenUp;
    private float fZCoordinateScreenDown;

    //intYDown + intXDown - left down corner of picture
    //intYUp + intYUp - right up corner
    GraphicItem(final int intXDown, final int intYDown,
    final int intXUp, final int intYUp) {
        this.pxYCoordinateScreenDown = intYDown;
        this.pxYCoordinateScreenUp = intYUp;
        this.pxXCoordianteScreenDown = intXDown;
        this.pxXCoordianteScreenUp = intXUp;
        this.fYCoordinateScreenDown = gameConstants.heightToFloat(intYDown);
        this.fYCoordinateScreenUp = gameConstants.heightToFloat(intYUp);
        this.fXCoordianteScreenDown = gameConstants.widthToFloat(intXDown);
        this.fXCoordianteScreenUp = gameConstants.widthToFloat(intXUp);
    }

    GraphicItem() {

    }

    //TODO Should be overrided in subclass or make it as design
    public void drawGraphicItem(GL10 _gl) {
        return;
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

    public final FloatBuffer createAndFillFloat(float[] _massive) {
        int len = _massive.length;
        ByteBuffer bb = ByteBuffer.allocateDirect(len * FLOAT_FIELD_SIZE);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer result = bb.asFloatBuffer();
        result.put(_massive);
        result.position(0);
        return result;
    }

    int sizeShortBuf;
    public void drawLines(GL10 gl) {
        gl.glColor4f(0, 0, 0, 1);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, floatBufferOfPosition);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDrawElements(GL10.GL_LINES, sizeShortBuf, GL10.GL_UNSIGNED_SHORT, shortBufferOfIndexes);
    }

    public boolean isDrawPicture() { return isDrawPicture; }
    public void setIsDrawPicture(boolean isDrawPicture) { this.isDrawPicture = isDrawPicture; }

}
