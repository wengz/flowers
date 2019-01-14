package com.example.wengzc.flowers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FaceRenderFactory {

    public static FaceRender getRender (FaceRender.Type type, FlowerBaseInfo baseInfo){
        switch (type){
            case DEFAULT:
                return new DefaultFaceRender(baseInfo);
        }
        return null;
    }

    public static class DefaultFaceRender extends FaceRender {

        private FlowerBaseInfo baseInfo;
        private Paint paint;
        private Paint fillPaint;

        private DefaultFaceRender (FlowerBaseInfo baseInfo){
            this.baseInfo = baseInfo;
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(Render.STROKE_WIDTH);
            paint.setAntiAlias(true);

            fillPaint = new Paint();
            fillPaint.setColor(Color.YELLOW);
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setStrokeWidth(1);
            fillPaint.setAntiAlias(true);
        }

        @Override
        public void renderFace(Canvas canvas, double centerX, double centerY, double baseR) {
            double cX = getCenterX();
            double cY = getCenterY();
            double r = getBaseR();

            canvas.drawCircle(0, 0, (float)r, fillPaint);
            canvas.drawCircle(0, 0, (float)r, paint);
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
