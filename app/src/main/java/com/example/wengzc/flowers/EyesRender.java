package com.example.wengzc.flowers;

import android.graphics.Canvas;

public abstract class EyesRender implements Render, FlowerBaseInfo{

    public enum Type{
        DEFAULT

    }

    enum Direction {

        LEFT,

        RIGHT;
    }

    @Override
    public void render(Canvas canvas) {
        renderEyes(canvas, getCenterX(), getCenterY(), getBaseR());
    }

    /**
     * 绘制眼睛
     */
    public abstract void renderEyes (Canvas canvas, double centerX, double centerY, double baseR);
}
