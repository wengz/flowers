package com.example.wengzc.flowers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.animation.OvershootInterpolator;

import java.util.Timer;
import java.util.TimerTask;

public class Flower implements Render{

    private PetalRender petalRender;
    private FaceRender faceRender;
    private EyesRender eyesRender;
    private MouthRender mouthRender;
    private FlowerBaseInfo baseInfo;
    private ViewHolder viewHolder;
    private long id;

    //初始化显示动画时间
    private static final int PRESENT_ANIMATION_DURATION = 500;
    private boolean initedPresentAnimation;
    private FlowerBaseInfo baseInfoBackup;
    private long animaStartTime;
    private long animaEndTime;
    private Timer animaTimer;
    private OvershootInterpolator overshootInterpolator;
    private Handler handler;

    private Flower (Builder builder){
        this.petalRender = builder.petalRender;
        this.faceRender = builder.faceRender;
        this.eyesRender = builder.eyesRender;
        this.mouthRender = builder.mouthRender;
        this.baseInfo = builder.baseInfo;
        this.initedPresentAnimation = false;
        this.overshootInterpolator = new OvershootInterpolator();
        this.viewHolder = builder.viewHolder;
        handler = new Handler();
        id = System.currentTimeMillis();

        startPresentAnimation();
    }

    public boolean initedPresentAnimation (){
        return initedPresentAnimation;
    }

    private void startPresentAnimation (){
        animaStartTime = System.currentTimeMillis();
        animaEndTime = animaStartTime + PRESENT_ANIMATION_DURATION;
        baseInfoBackup = new Builder.BaseInfo(baseInfo.getCenterX(), baseInfo.getCenterY(), baseInfo.getBaseR());
        ((Builder.BaseInfo)baseInfo).setR(0);

        animaTimer = new Timer();
        animaTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        long now = System.currentTimeMillis();
                        if (now >= animaEndTime){
                            finishPresentAnimation();
                        }else{
                            Builder.BaseInfo bInfo = (Builder.BaseInfo) baseInfo;
                            double nr = baseInfoBackup.getBaseR() * overshootInterpolator.getInterpolation( (now - animaStartTime) * 1.0f /PRESENT_ANIMATION_DURATION);
                            bInfo.setR(nr);
                            if (viewHolder != null){
                                viewHolder.postInvalidate();
                            }
                        }
                    }
                });

            }
        }, PRESENT_ANIMATION_DURATION/20, PRESENT_ANIMATION_DURATION/20);

    }

    private void finishPresentAnimation (){
        if (initedPresentAnimation){
            return;
        }

        if (animaTimer != null){
            animaTimer.cancel();
            animaTimer = null;
        }
        ((Builder.BaseInfo)baseInfo).setR(baseInfoBackup.getBaseR());
        baseInfoBackup = null;
        initedPresentAnimation = true;
        if (viewHolder != null){
            viewHolder.postInvalidate();
        }
    }

    public static class Builder {
        private PetalRender petalRender;
        private FaceRender faceRender;
        private EyesRender eyesRender;
        private MouthRender mouthRender;
        private FlowerBaseInfo baseInfo;
        private ViewHolder viewHolder;

        public Flower build (){
            return new Flower(this);
        }

        public Builder setHolderView (ViewHolder viewHolder){
            this.viewHolder = viewHolder;
            return this;
        }

        static class BaseInfo implements FlowerBaseInfo {

            double cX;
            double cY;
            double r;

            public BaseInfo (double x, double y, double r){
                this.cX = x;
                this.cY = y;
                this.r = r;
            }

            @Override
            public void setFlowerBaseInfo(FlowerBaseInfo flowerBaseInfo) {
                cX = flowerBaseInfo.getCenterX();
                cY = flowerBaseInfo.getCenterY();
                r = flowerBaseInfo.getBaseR();
            }

            @Override
            public double getCenterX() {
                return cX;
            }

            @Override
            public double getCenterY() {
                return cY;
            }

            @Override
            public double getBaseR() {
                return r;
            }

            public void setR (double nr){
                r = nr;
            }

        }

        Builder(final double centerX, final double centerY, final double baseR){
            baseInfo = new BaseInfo(centerX, centerY, baseR);
            petalRender = PetalRenderFactory.getRender(PetalRender.Type.COLOR_LOOP, baseInfo, new PetalRenderFactory.ColorLoopPetalRender.ConstrcutArgument(Color.WHITE));
            faceRender = FaceRenderFactory.getRender(FaceRender.Type.COLOR, baseInfo, new FaceRenderFactory.ColorFaceRender.ConstructArgument(Color.WHITE));
            eyesRender = EyesRenderFactory.getRener(EyesRender.Type.COLOR, baseInfo, new EyesRenderFactory.ColorEyesRender.ConstructArgument(Color.BLACK, Color.BLACK));
            mouthRender = MouthRenderFactory.getRender(MouthRender.Type.COLOR, baseInfo, new MouthRenderFactory.ColorMouthRender.ConstrcutArgument(Color.WHITE));
        }

        public PetalRender getPetalRender() {
            return petalRender;
        }

        public Builder setPetalRender(PetalRender petalRender) {
            this.petalRender = petalRender;
            this.petalRender.setFlowerBaseInfo(this.baseInfo);
            return this;
        }

        public FaceRender getFaceRender() {
            return faceRender;
        }

        public Builder setFaceRender(FaceRender faceRender) {
            this.faceRender = faceRender;
            this.faceRender.setFlowerBaseInfo(this.baseInfo);
            return this;
        }

        public EyesRender getEyesRender() {
            return eyesRender;
        }

        public Builder setEyesRender(EyesRender eyesRender) {
            this.eyesRender = eyesRender;
            this.eyesRender.setFlowerBaseInfo(this.baseInfo);
            return this;
        }

        public MouthRender getMouthRender() {
            return mouthRender;
        }

        public Builder setMouthRender(MouthRender mouthRender) {
            this.mouthRender = mouthRender;
            this.mouthRender.setFlowerBaseInfo(this.baseInfo);
            return this;
        }

        public FlowerBaseInfo getBaseInfo() {
            return baseInfo;
        }

        public Builder setBaseInfo(FlowerBaseInfo baseInfo) {
            this.baseInfo = baseInfo;
            return this;
        }

    }


    @Override
    public void render(Canvas canvas) {
        double centerX = baseInfo.getCenterX();
        double centerY = baseInfo.getCenterY();
        double r = baseInfo.getBaseR();
        if (r < 5){
            return;
        }

        canvas.save();
        canvas.translate((float)centerX, (float)centerY);

        petalRender.render(canvas);
        faceRender.render(canvas);
        eyesRender.render(canvas);
        mouthRender.render(canvas);

        canvas.restore();
    }
}
