package com.example.wengzc.flowers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class MouthRenderFactory {

    public static MouthRender getRender (MouthRender.Type type, FlowerBaseInfo baseInfo){
        switch (type){
            case DEFAULT:
                return new DefaultMouthRender(baseInfo);
        }
        return null;
    }

    public static class DefaultMouthRender extends MouthRender {

        private FlowerBaseInfo baseInfo;
        private Paint paint;
        private Paint fillPaint;

        private DefaultMouthRender (FlowerBaseInfo baseInfo){
            this.baseInfo = baseInfo;
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(Render.STROKE_WIDTH);
            paint.setAntiAlias(true);

            fillPaint = new Paint();
            fillPaint.setColor(Color.BLUE);
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setStrokeWidth(1);
            fillPaint.setAntiAlias(true);
        }

        @Override
        public void renderMouth(Canvas canvas, double centerX, double centerY, double baseR) {
            double cX = getCenterX();
            double cY = getCenterY();
            float r = (float)getBaseR();

            Path mouthPath = new Path();
            mouthPath.moveTo(-r * 0.8f, 0);
            mouthPath.quadTo( 0, -r / 5f,r * 0.8f, 0);
            mouthPath.quadTo(r * 0.5f, r * 0.8f, 0, r * 0.8f);
            mouthPath.quadTo(-r * 0.5f, r * 0.8f,-r * 0.8f, 0);
            canvas.drawPath(mouthPath, fillPaint);
            canvas.drawPath(mouthPath, paint);
        }

        @Override
        public double getCenterX() {
            return baseInfo.getCenterX();
        }

        @Override
        public double getCenterY() {
            return baseInfo.getCenterY();
        }

        @Override
        public double getBaseR() {
            return baseInfo.getBaseR();
        }
    }
}
