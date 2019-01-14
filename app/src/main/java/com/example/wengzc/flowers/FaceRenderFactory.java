package com.example.wengzc.flowers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FaceRenderFactory {

    public static FaceRender getRender (FaceRender.Type type){
        return getRender(type, null, null);
    }

    public static FaceRender getRender (FaceRender.Type type, FlowerBaseInfo baseInfo){
        return getRender(type, baseInfo, null);
    }

    public static FaceRender getRender (FaceRender.Type type, FaceRender.ConstructArgument constructArgument){
        return getRender(type, null, constructArgument);
    }

    public static FaceRender getRender (FaceRender.Type type, FlowerBaseInfo baseInfo, FaceRender.ConstructArgument constructArgument){
        switch (type){
            case COLOR:
                return new ColorFaceRender(baseInfo, (ColorFaceRender.ConstructArgument) constructArgument);
        }
        return null;
    }

    public static class ColorFaceRender extends FaceRender {

        public static class ConstructArgument extends FaceRender.ConstructArgument {

            int color;

            public ConstructArgument (int color){
                this.color = color;
            }

        }

        private FlowerBaseInfo baseInfo;
        private Paint paint;
        private Paint fillPaint;

        private ColorFaceRender(FlowerBaseInfo baseInfo, ConstructArgument constructArgument){
            this.baseInfo = baseInfo;
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(Render.STROKE_WIDTH);
            paint.setAntiAlias(true);

            fillPaint = new Paint();
            fillPaint.setColor(constructArgument.color);
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
        public void setFlowerBaseInfo(FlowerBaseInfo flowerBaseInfo) {
            this.baseInfo = flowerBaseInfo;
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
