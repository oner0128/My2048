package com.example.hrong.my2048;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hrong on 2016/4/15.
 */
public class AnotherActivity extends AppCompatActivity{
private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        gridView= (GridView) findViewById(R.id.gridView);
        Map<String,Card> cardMap=new HashMap<>();
        List<Map<String,Card>> mapList=new ArrayList<>();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
            cardMap.put("card",new Card(this));
                mapList.add(cardMap);
            }
        }

//        SimpleAdapter simpleAdapter=new SimpleAdapter(this,mapList, R.layout.item,"card", R.id.textssss)
//        gridView.setAdapter(simpleAdapter);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                }
                if (Math.abs(offsetX) > Math.abs(offsetY)) {
                    if (offsetX > 5) {
                        swipeRight();
                    } else if (offsetX < -5)
                        swipeLeft();
                } else {
                    if (offsetY < -5) {
                        swipeUp();
                    } else if (offsetY > 5)
                        swipeDown();
                }
                return true;
            }
        });
    }

    private void swipeLeft() {
        System.out.println("swipeLeft");
    }

    private void swipeUp() {
        System.out.println("swipeUp");
    }
    private void swipeRight() {
        System.out.println("swipeRight");
    }
    private void swipeDown() {
        System.out.println("swipeDown");
    }
}
