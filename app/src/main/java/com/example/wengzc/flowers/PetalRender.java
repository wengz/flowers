package com.example.wengzc.flowers;

import android.graphics.Canvas;

public abstract class PetalRender implements Render, FlowerBaseInfo{

    public enum Type {
        COLOR_LOOP,
    }


    public static abstract class ConstructArgument {


    }


    @Override
    public void render(Canvas canvas) {
        renderPetals(canvas, getCenterX(), getCenterY(), getBaseR());
    }

    /**
     * 绘制花瓣
     */
    public abstract void renderPetals (Canvas canvas, double centerX, double centerY, double baseR);
}
