package com.example.wengzc.flowers;

import android.graphics.Canvas;

public abstract class FaceRender implements Render, FlowerBaseInfo{

    public enum Type {
        COLOR
    }


    public static abstract class ConstructArgument {

    }


    @Override
    public void render(Canvas canvas) {
        renderFace(canvas, getCenterX(), getCenterY(), getBaseR());
    }

    /**
     * 绘制脸
     */
    public abstract void renderFace (Canvas canvas, double centerX, double centerY, double baseR);
}
