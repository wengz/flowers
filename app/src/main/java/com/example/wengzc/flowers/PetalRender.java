package com.example.wengzc.flowers;

import android.graphics.Canvas;

public abstract class PetalRender implements Render, FlowerBaseInfo{

    public enum Type {
        DEFAULT,

        LOOP,
    }

    @Override
    public void render(Canvas canvas) {
        renderPetals(canvas, getCenterX(), getCenterY(), getBaseR());
    }

    public void setFlowerBaseInfo (FlowerBaseInfo flowerBaseInfo){

    }

    /**
     * 绘制花瓣
     */
    public abstract void renderPetals (Canvas canvas, double centerX, double centerY, double baseR);
}
