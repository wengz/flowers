package com.example.wengzc.flowers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class MouthRenderFactory {

    public static MouthRender getRender (MouthRender.Type type){
        return getRender(type, null, null);
    }

    public static MouthRender getRender (MouthRender.Type type, MouthRender.ConstructArgument constructArgument){
        return getRender(type, null, constructArgument);
    }

    public static MouthRender getRender (MouthRender.Type type, FlowerBaseInfo baseInfo){
        return getRender( type, baseInfo, null);
    }

    public static MouthRender getRender (MouthRender.Type type, FlowerBaseInfo baseInfo, MouthRender.ConstructArgument constructArgument){
        switch (type){
            case COLOR:
                return new ColorMouthRender(baseInfo, (ColorMouthRender.ConstrcutArgument) constructArgument);
        }
        return null;
    }


    public static class ColorMouthRender extends MouthRender {


        public static class ConstrcutArgument extends ConstructArgument {
            int color;

            public ConstrcutArgument (int color){
                this.color = color;
            }
        }

        private FlowerBaseInfo baseInfo;
        private Paint paint;
        private Paint fillPaint;

        private ColorMouthRender(FlowerBaseInfo baseInfo, ConstrcutArgument constructArgument){
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
