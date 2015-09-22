package com.example.betmansmall.towerdefence_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.MoreAsserts;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements View.OnTouchListener {
    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        drawView.setOnTouchListener(this);
        drawView.setBackgroundColor(Color.WHITE);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(drawView);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.main);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getActionMasked();

        Log.d("TTW", "onTouch() -- X: " + x + " Y: " + y);
        if(action == MotionEvent.ACTION_DOWN) {
            Log.d("TTW", "onTouch() -- ACTION_BUTTON_PRESS");
            drawView.mousePressEvent(x, y);
        } else if(action == MotionEvent.ACTION_UP) {
            Log.d("TTW", "onTouch() -- ACTION_BUTTON_RELEASE");
//            drawView.mouseReleseEvent(x, y);
        }
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
