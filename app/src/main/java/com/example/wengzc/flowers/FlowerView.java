package com.example.wengzc.flowers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

public class FlowerView extends View {

    private List<Flower> flowers;

    public FlowerView(Context context) {
        super(context);
        init();
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_UP:
                handleClick(event);
                break;
        }
        return true;
    }

    private void handleClick (MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        Flower flower = FlowerFactory.makeFlower(x, y, this);
        flowers.add(flower);
        invalidate();
    }

    private void init() {
        flowers = new ArrayList();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (Flower flower : flowers){
            flower.render(canvas);
        }
    }
}
