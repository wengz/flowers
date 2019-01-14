package com.example.wengzc.flowers;

import android.graphics.Color;
import android.view.View;

import java.util.Random;

public class FlowerFactory {

    private static Random random;
    private static final double R_LIMIT = 200;
    static private int xLimit;
    static private int yLimit;

    static {
        random = new Random();
    }

    public static void initLimit (int argXLimit, int argYLimit){
        xLimit = argXLimit;
        yLimit = argYLimit;
    }

    public static Flower makeFlower (float cX, float cY, View holderView){
        double r = 0;
        double centerX = cX;
        double centerY = cY;

        while ( r < 20){
            r = R_LIMIT * random.nextDouble();
        }

        int[] petalColor = null;
        if (random.nextBoolean()){
            petalColor = new int[1];
            petalColor[0] = ColorPool.randomColor();
        }else{
            petalColor = new int[2];
            petalColor[0] = ColorPool.randomColor();
            petalColor[1] = ColorPool.randomColor();
        }

        Flower flower = new Flower.Builder(centerX, centerY, r)
                .setHolderView(holderView)
                .setPetalRender(PetalRenderFactory.getRender(PetalRender.Type.COLOR_LOOP, new PetalRenderFactory.ColorLoopPetalRender.ConstrcutArgument(petalColor)))
                .setFaceRender(FaceRenderFactory.getRender(FaceRender.Type.COLOR, new FaceRenderFactory.ColorFaceRender.ConstructArgument(ColorPool.randomColor())))
                .setEyesRender(EyesRenderFactory.getRener(EyesRender.Type.COLOR, new EyesRenderFactory.ColorEyesRender.ConstructArgument(ColorPool.randomColor(), ColorPool.randomColor())))
                .setMouthRender(MouthRenderFactory.getRender(MouthRender.Type.COLOR, new MouthRenderFactory.ColorMouthRender.ConstrcutArgument(ColorPool.randomColor())))
                .build();
        return flower;
    }


}
