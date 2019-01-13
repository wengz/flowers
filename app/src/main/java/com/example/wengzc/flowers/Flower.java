package com.example.wengzc.flowers;

import android.graphics.Canvas;
import android.graphics.Color;

public class Flower implements Render{

    private PetalRender petalRender;
    private FaceRender faceRender;
    private EyesRender eyesRender;
    private MouthRender mouthRender;
    private FlowerBaseInfo baseInfo;

    private Flower (Builder builder){
        this.petalRender = builder.petalRender;
        this.faceRender = builder.faceRender;
        this.eyesRender = builder.eyesRender;
        this.mouthRender = builder.mouthRender;
        this.baseInfo = builder.baseInfo;
    }

    public static class Builder {
        private PetalRender petalRender;
        private FaceRender faceRender;
        private EyesRender eyesRender;
        private MouthRender mouthRender;
        private FlowerBaseInfo baseInfo;

        public Flower build (){
            return new Flower(this);
        }

        Builder(final double centerX, final double centerY, final double baseR){
            baseInfo = new FlowerBaseInfo() {
                @Override
                public double getCenterX() {
                    return centerX;
                }

                @Override
                public double getCenterY() {
                    return centerY;
                }

                @Override
                public double getBaseR() {
                    return baseR;
                }
            };
            petalRender = PetalRenderFactory.getRender(PetalRender.Type.DEFAULT, baseInfo);
            faceRender = FaceRenderFactory.getRender(FaceRender.Type.DEFAULT, baseInfo);
            eyesRender = EyesRenderFactory.getRener(EyesRender.Type.DEFAULT, baseInfo);
            mouthRender = MouthRenderFactory.getRender(MouthRender.Type.DEFAULT, baseInfo);
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

        public void setFaceRender(FaceRender faceRender) {
            this.faceRender = faceRender;
        }

        public EyesRender getEyesRender() {
            return eyesRender;
        }

        public void setEyesRender(EyesRender eyesRender) {
            this.eyesRender = eyesRender;
        }

        public MouthRender getMouthRender() {
            return mouthRender;
        }

        public void setMouthRender(MouthRender mouthRender) {
            this.mouthRender = mouthRender;
        }

        public FlowerBaseInfo getBaseInfo() {
            return baseInfo;
        }

        public void setBaseInfo(FlowerBaseInfo baseInfo) {
            this.baseInfo = baseInfo;
        }
    }


    @Override
    public void render(Canvas canvas) {
        double centerX = baseInfo.getCenterX();
        double centerY = baseInfo.getCenterY();
        canvas.drawColor(Color.WHITE);
        canvas.save();
        canvas.translate((float)centerX, (float)centerY);

        petalRender.render(canvas);
        faceRender.render(canvas);
        eyesRender.render(canvas);
        mouthRender.render(canvas);

        canvas.restore();
    }
}
