package com.example.wengzc.flowers;

import android.graphics.Canvas;

public abstract class EyesRender implements Render, FlowerBaseInfo{

    public enum Type{
        COLOR

    }

    enum Direction {

        LEFT,

        RIGHT;
    }

    public static abstract class ConstructArgument {

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
