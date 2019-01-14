package com.example.wengzc.flowers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class PetalRenderFactory {

    public static PetalRender getRender (PetalRender.Type type){
        return getRender(type, null, null);
    }

    public static PetalRender getRender (PetalRender.Type type, PetalRender.ConstructArgument constructArgument){
        return getRender(type, null, constructArgument);
    }

    public static PetalRender getRender (PetalRender.Type type, FlowerBaseInfo baseInfo){
        return getRender(type, baseInfo, null);
    }


    public static PetalRender getRender (PetalRender.Type type, FlowerBaseInfo baseInfo, PetalRender.ConstructArgument constructArgument){
        switch (type){
            case COLOR_LOOP:
                return new ColorLoopPetalRender(baseInfo, (ColorLoopPetalRender.ConstrcutArgument)constructArgument);
        }
        return null;
    }


    public static class ColorLoopPetalRender extends PetalRender {

        private FlowerBaseInfo baseInfo;
        private Paint strokePaint;
        private Paint[] fillPaint;
        private int[] colors;

        public static class ConstrcutArgument extends ConstructArgument {
            private int[] colors;

            public ConstrcutArgument (int ... colors){
                this.colors = colors;
            }
        }

        @Override
        public void setFlowerBaseInfo(FlowerBaseInfo flowerBaseInfo) {
            this.baseInfo = flowerBaseInfo;
        }

        private ColorLoopPetalRender (FlowerBaseInfo baseInfo, ConstrcutArgument constrcutArgument){
            this.baseInfo = baseInfo;
            this.colors = constrcutArgument.colors;

            strokePaint = new Paint();
            strokePaint.setColor(Color.BLACK);
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setStrokeWidth(Render.STROKE_WIDTH);
            strokePaint.setAntiAlias(true);

            fillPaint = new Paint[colors.length];
            for (int i = 0; i < colors.length; i++){
                fillPaint[i] = new Paint();
                fillPaint[i].setStyle(Paint.Style.FILL);
                fillPaint[i].setColor(colors[i]);
                fillPaint[i].setStrokeWidth(1);
                fillPaint[i].setAntiAlias(true);
            }
        }

        @Override
        public void renderPetals(Canvas canvas, double centerX, double centerY, double baseR) {
            double r = getBaseR();
            double R = r * 2;
            double cX = getCenterX();
            double cY = getCenterY();
            double petal_rate = 1.25;
            int colorPaitIndex = 0;
            int colorPaintLength = fillPaint.length;

            for ( int i = 0; i * 30 < 360 ; i++ ){
                double angle_m =  i* 30;
                double angle_m_rad = Math.toRadians(angle_m);
                double x_m = Math.cos(angle_m_rad) * R * petal_rate;
                double y_m = Math.sin(angle_m_rad) * R * petal_rate;

                double angle_s =  i* 30 - 15;
                double angle_s_rad = Math.toRadians(angle_s);
                double x_s = Math.cos(angle_s_rad) * R;
                double y_s = Math.sin(angle_s_rad) * R;
                double x_s_inner = Math.cos(angle_s_rad) * r;
                double y_s_inner = Math.sin(angle_s_rad) * r;

                double control_x_s_angle = angle_s + (angle_m - angle_s) * 0.2;
                double control_x_s_angle_rad = Math.toRadians(control_x_s_angle);
                double control_x_s = Math.cos(control_x_s_angle_rad) * R * petal_rate;
                double control_y_s = Math.sin(control_x_s_angle_rad) * R * petal_rate;

                double angle_e =  i* 30 + 15;
                double angle_e_rad = Math.toRadians(angle_e);
                double x_e = Math.cos(angle_e_rad) * R;
                double y_e = Math.sin(angle_e_rad) * R;
                double x_e_inner = Math.cos(angle_e_rad) * r;
                double y_e_inner = Math.sin(angle_e_rad) * r;

                double control_x_e_angle =  angle_e - (angle_e- angle_m) * 0.2;
                double control_x_e_angle_rad = Math.toRadians(control_x_e_angle);
                double control_x_e = Math.cos(control_x_e_angle_rad) * R * petal_rate;
                double control_y_e = Math.sin(control_x_e_angle_rad) * R * petal_rate;

                Path path = new Path();
                path.moveTo((float)x_s_inner, (float)y_s_inner);
                path.lineTo((float)x_s, (float)y_s);
                path.quadTo((float)control_x_s, (float)control_y_s,(float)x_m, (float)y_m);
                path.quadTo((float)control_x_e, (float)control_y_e,(float)x_e, (float)y_e);
                path.lineTo((float)x_e_inner, (float)y_e_inner);

                canvas.drawPath(path, fillPaint[colorPaitIndex]);
                canvas.drawPath(path, strokePaint);

                colorPaitIndex++;
                colorPaitIndex %= colorPaintLength;
            }
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
