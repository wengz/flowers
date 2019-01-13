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

    private Flower flower;

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
        flower = new Flower.Builder(500, 500, 200)
                .setPetalRender(PetalRenderFactory.getRender(PetalRender.Type.LOOP))
                .build();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        flower.render(canvas);
    }
}
