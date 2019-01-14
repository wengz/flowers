package com.example.wengzc.flowers;

import android.graphics.Canvas;

public abstract class MouthRender implements Render, FlowerBaseInfo{

    public enum Type {
        DEFAULT
    }

    @Override
    public void render(Canvas canvas) {
        renderMouth(canvas, getCenterX(), getCenterY(), getBaseR());
    }

    /**
     * 绘制嘴巴
     */
    public abstract void renderMouth (Canvas canvas, double centerX, double centerY, double baseR);
}
