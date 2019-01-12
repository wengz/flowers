package com.example.wengzc.flowers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class FlowerView extends View {

    private Paint mPaint;

    public FlowerView(Context context) {
        super(context);
        init();
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
    }

    private Bitmap createEyeContainer (){
        Bitmap bitDstMap = Bitmap.createBitmap(102, 202, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitDstMap);
        canvas.translate(50, 100);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        RectF rectF = new RectF(-50, -100, 50, 100);
        canvas.drawOval(rectF, paint);
        return bitDstMap;
    }

    private Bitmap createEyeInnerHoleBig (){
        Bitmap bitDstMap = Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitDstMap);
        canvas.translate(50, 100);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        RectF rectFInner_1 = new RectF(0, -80, 42, 20);
        canvas.drawOval(rectFInner_1, paint);
        return bitDstMap;
    }

    private Bitmap createEyeInnerHoleSmall (){
        Bitmap bitDstMap = Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitDstMap);
        canvas.translate(50, 100);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        RectF rectFInner_2 = new RectF(-48, 50, 0, 100);
        canvas.drawOval(rectFInner_2, paint);
        return bitDstMap;
    }


    private Bitmap createEye (){
        Bitmap bitDstMap = Bitmap.createBitmap(102, 202, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitDstMap);
        //int saveCount = canvas.saveLayer(new RectF(0, 0, 102, 202), mPaint, Canvas.ALL_SAVE_FLAG);
        Bitmap eyeContainer = createEyeContainer();
        canvas.drawBitmap(eyeContainer, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Bitmap eyeInnerSmall = createEyeInnerHoleSmall();
        canvas.drawBitmap(eyeInnerSmall, 0, 0, mPaint);
        Bitmap eyeInnerBig = createEyeInnerHoleBig();
        canvas.drawBitmap(eyeInnerBig, 0, 0, mPaint);
        mPaint.setXfermode(null);
        //canvas.restoreToCount(saveCount);
        return bitDstMap;
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawColor(Color.YELLOW);
//        canvas.save();
//        canvas.translate(300, 300);
//        canvas.rotate(-30);
//        Bitmap eye = createEye();
//        canvas.drawBitmap(eye, 0, 0, mPaint);
//        canvas.restore();


        canvas.drawColor(Color.WHITE);
        canvas.save();
        canvas.translate(350, 350);
        int r = 100;
        for ( int i = 0; i * 30 < 360 ; i++ ){
            int R = r * 2;
            double angle_m =  i* 30;
            double angle_m_rad = Math.toRadians(angle_m);
            double x_m = Math.cos(angle_m_rad) * R * 1.35;
            double y_m = Math.sin(angle_m_rad) * R * 1.35;

            double angle_s =  i* 30 - 15;
            double angle_s_rad = Math.toRadians(angle_s);
            double x_s = Math.cos(angle_s_rad) * R;
            double y_s = Math.sin(angle_s_rad) * R;
            double x_s_inner = Math.cos(angle_s_rad) * r;
            double y_s_inner = Math.sin(angle_s_rad) * r;

            double angle_e =  i* 30 + 15;
            double angle_e_rad = Math.toRadians(angle_e);
            double x_e = Math.cos(angle_e_rad) * R;
            double y_e = Math.sin(angle_e_rad) * R;
            double x_e_inner = Math.cos(angle_e_rad) * r;
            double y_e_inner = Math.sin(angle_e_rad) * r;

            Path path = new Path();
            path.moveTo((float)x_s_inner, (float)y_s_inner);
            path.lineTo((float)x_s, (float)y_s);
            path.quadTo((float)x_m, (float)y_m, (float)x_e, (float)y_e);
            path.lineTo((float)x_e_inner, (float)y_e_inner);
            canvas.drawPath(path, mPaint);
        }

        Path mouthPath = new Path();
        mouthPath.moveTo(-r * 0.8f, 0);
        mouthPath.quadTo( 0, -r / 5f,r * 0.8f, 0);
        mouthPath.quadTo(r * 0.5f, r * 0.8f, 0, r * 0.8f);
        mouthPath.quadTo(-r * 0.5f, r * 0.8f,-r * 0.8f, 0);
        canvas.drawPath(mouthPath, mPaint);

        canvas.drawCircle(0, 0, r, mPaint);
        canvas.restore();

    }
}
