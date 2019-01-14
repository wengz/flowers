package com.example.wengzc.flowers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;

import static com.example.wengzc.flowers.EyesRender.Direction.LEFT;
import static com.example.wengzc.flowers.EyesRender.Direction.RIGHT;

public class EyesRenderFactory {

    public static EyesRender getRener(EyesRender.Type type){
        return getRener( type, null, null);
    }

    public static EyesRender getRener(EyesRender.Type type, FlowerBaseInfo baseInfo){
        return getRener( type, baseInfo, null);
    }

    public static EyesRender getRener(EyesRender.Type type, EyesRender.ConstructArgument constructArgument){
        return getRener( type, null, constructArgument);
    }

    public static EyesRender getRener(EyesRender.Type type, FlowerBaseInfo baseInfo, EyesRender.ConstructArgument constructArgument){
        switch (type){
            case COLOR:
                return new ColorEyesRender(baseInfo, (ColorEyesRender.ConstructArgument) constructArgument);
        }
        return null;
    }

    public static class ColorEyesRender extends EyesRender {


        public static class ConstructArgument extends EyesRender.ConstructArgument {
            int leftEyeColor;
            int rightEyeColor;

            public ConstructArgument (int leftEyeColor, int rightEyeColor){
                this.leftEyeColor = leftEyeColor;
                this.rightEyeColor = rightEyeColor;
            }
        }


        private FlowerBaseInfo baseInfo;
        private Paint paint;
        private int leftEyeColor;
        private int rightEyeColor;

        private ColorEyesRender(FlowerBaseInfo baseInfo, ConstructArgument constructArgument){
            this.baseInfo = baseInfo;
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            paint.setAntiAlias(true);

            leftEyeColor = constructArgument.leftEyeColor;
            rightEyeColor = constructArgument.rightEyeColor;
        }

        private Bitmap createEyeContainer (int r, Direction direction){
            Bitmap bitDstMap = Bitmap.createBitmap(r+2, r*2+2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitDstMap);
            canvas.translate(r/2, r);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            if (direction == LEFT){
                paint.setColor(leftEyeColor);
            }else if (direction == RIGHT){
                paint.setColor(rightEyeColor);
            }

            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1);
            RectF rectF = new RectF(-(r/2), -r, r/2, r);
            canvas.drawOval(rectF, paint);
            return bitDstMap;
        }

        private Bitmap createEyeInnerHoleBig (int r, Direction direction){
            if (r <= 0 ){
                r = 1;
            }
            Bitmap bitDstMap = Bitmap.createBitmap(r, r*2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitDstMap);
            canvas.translate(r/2, r);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1);
            RectF rectFInner_1 = null;
            if (direction == LEFT){
                rectFInner_1 = new RectF(-r * 0.4f - 2, -(r * 0.8f), 0, r * 0.2f);
            }else if (direction == RIGHT){
                rectFInner_1 = new RectF(0, -(r * 0.8f), r * 0.4f + 2, r * 0.2f);
            }
            canvas.drawOval(rectFInner_1, paint);
            return bitDstMap;
        }

        private Bitmap createEyeInnerHoleSmall (int r, Direction direction){
            if (r <= 0){
                r = 1;
            }
            Bitmap bitDstMap = Bitmap.createBitmap(r, r * 2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitDstMap);
            canvas.translate(r/2, r);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1);
            RectF rectFInner_2 = null;
            if (direction == LEFT){
                rectFInner_2 = new RectF(0,r * 0.5f, r * 0.5f -2, r);
            }else if (direction == RIGHT){
                rectFInner_2 = new RectF(-(r * 0.5f -2), r * 0.5f, 0, r);
            }
            canvas.drawOval(rectFInner_2, paint);
            return bitDstMap;
        }

        private Bitmap createEye (int r, Direction direction){
            Bitmap bitDstMap = Bitmap.createBitmap(r+2, r*2+2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitDstMap);
            //int saveCount = canvas.saveLayer(new RectF(0, 0, 102, 202), mPaint, Canvas.ALL_SAVE_FLAG);
            Bitmap eyeContainer = createEyeContainer(r, direction);
            canvas.drawBitmap(eyeContainer, 0, 0, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            Bitmap eyeInnerSmall = createEyeInnerHoleSmall(r, direction);
            canvas.drawBitmap(eyeInnerSmall, 0, 0, paint);
            Bitmap eyeInnerBig = createEyeInnerHoleBig(r, direction);
            canvas.drawBitmap(eyeInnerBig, 0, 0, paint);
            paint.setXfermode(null);
            //canvas.restoreToCount(saveCount);
            return bitDstMap;
        }

        @Override
        public void renderEyes(Canvas canvas, double centerX, double centerY, double baseR) {
            double r = getBaseR();
            double eye_dis_rate = 0.4;
            double eye_size_rate = 0.15;

            canvas.save();
            double eyeWidth = r * eye_size_rate;
            double eyeLeft = r * eye_dis_rate - eyeWidth/2;
            double eyeTop = -r * eye_dis_rate - eyeWidth;
            canvas.rotate(-30, (float)(eyeLeft + eyeWidth/2), (float)(eyeTop + eyeWidth));
            Bitmap eye = createEye((int)eyeWidth, RIGHT);
            canvas.drawBitmap(eye, (float)eyeLeft, (float)eyeTop, paint);
            canvas.restore();

            canvas.save();
            double leftEyeWidth = r * eye_size_rate;
            double leftEyeLeft = (-r * eye_dis_rate) - leftEyeWidth/2;
            double leftEyeTop = -r * eye_dis_rate - eyeWidth;
            canvas.rotate(30, (float)(leftEyeLeft + leftEyeWidth/2), (float)(leftEyeTop + eyeWidth));
            Bitmap leftEye = createEye((int)eyeWidth, LEFT);
            canvas.drawBitmap(leftEye, (float)leftEyeLeft, (float)leftEyeTop, paint);
            canvas.restore();
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
