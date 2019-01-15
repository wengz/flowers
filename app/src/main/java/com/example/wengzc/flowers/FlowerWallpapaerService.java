package com.example.wengzc.flowers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

public class FlowerWallpapaerService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new FlowerWallpaperEngin();
    }

    private class FlowerWallpaperEngin extends Engine {

        private Handler handler;
        private Paint paint;
        private Bitmap board;
        private Canvas boardCanvas;
        private List<Flower> flowers;
        private ViewHolder viewHolder;
        private int backgroundColor;

        private class FlowerWallPaperViewHolder implements ViewHolder {

            @Override
            public void postInvalidate() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SurfaceHolder holder = getSurfaceHolder();
                        Canvas canvas = null;
                        try {
                            canvas = holder.lockCanvas();
                            if (canvas != null) {

                                //绘制已完成的初始化动画的元素到缓存中，然后将这些元素从队列中移除
                                for (int i = 0; i < flowers.size(); i++){
                                    Flower flower = flowers.get(i);
                                    if (flower.initedPresentAnimation()){
                                        flower.render(boardCanvas);
                                        flowers.remove(i);
                                        i--;
                                    }
                                }

                                //绘制缓存到显示的画布中
                                canvas.drawColor(backgroundColor);
                                canvas.drawBitmap(board,0, 0, null);

                                //绘制正在进行初始化动画的元素到画布中
                                for (int i = 0; i < flowers.size(); i++){
                                    Flower flower = flowers.get(i);
                                    if (!flower.initedPresentAnimation()){
                                        flower.render(canvas);
                                    }
                                }

                            }
                        } finally {
                            if (canvas != null) {
                                holder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                });
            }
        }

        public FlowerWallpaperEngin (){
            handler = new Handler();
            flowers = new ArrayList<>();
            paint = new Paint();
            paint.setAntiAlias(true);
            viewHolder = new FlowerWallPaperViewHolder();
            backgroundColor = ColorPool.colors.get(9);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            board = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            boardCanvas = new Canvas(board);

            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(backgroundColor);
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }


        private float touchStartX;
        private float touchStartY;
        private float touchEndX;
        private float touchEndY;
        private long touchStartTime;
        private static final float TOUCH_DIS_LIMIT = 25;
        //private static final float TOUCH_TIME_LIMIT = 1500;

        @Override
        public void onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    touchStartX = x;
                    touchStartY = y;
                    //touchStartTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_UP:
                    double dis = distance(x, y, touchStartX, touchStartY);
                    long now = System.currentTimeMillis();
                    //long timeDuration = now - touchStartTime;
                    if (dis < TOUCH_DIS_LIMIT /* && timeDuration > TOUCH_TIME_LIMIT */){
                        makeNewFlower(x, y);
                    }
                    break;
            }
        }

        private double distance (float x1, float y1, float x2, float y2){
            return Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) );
        }

        private void makeNewFlower (float x, float y){
            Flower flower = FlowerFactory.makeFlower(x, y, viewHolder);
            flowers.add(flower);
        }

    }
}
