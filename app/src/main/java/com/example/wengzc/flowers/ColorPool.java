package com.example.wengzc.flowers;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorPool {

    static List<Integer> colors;
    static Random random;

    static {
        colors = new ArrayList<>();
        random = new Random();
    }

    public static void init (Context context){
        colors.add( ContextCompat.getColor(context, R.color.flower_color_66E0F3));
        colors.add( ContextCompat.getColor(context, R.color.flower_color_FCFDFC));
        colors.add( ContextCompat.getColor(context, R.color.flower_color_FF3322));
        colors.add( ContextCompat.getColor(context, R.color.flower_color_D7DAE6));
        colors.add( ContextCompat.getColor(context, R.color.flower_color_FFF038));
        colors.add( ContextCompat.getColor(context, R.color.flower_color_DDED80));
        colors.add( ContextCompat.getColor(context, R.color.flower_color_FF89B7));
        colors.add( ContextCompat.getColor(context, R.color.flower_color_6F4FB8));
        colors.add( ContextCompat.getColor(context, R.color.flower_color_14150A));
    }

    public static int randomColor (){
        int rIndex = random.nextInt(colors.size());
        return colors.get(rIndex);
    }


    /**
     * 大概率应该为白色，其他有少数几个可选颜色
     */
    public static int randomFaceColor (){
        int r = random.nextInt(10);
        if (r == 9){
            return colors.get(4);
        }else if (r == 8){
            return colors.get(8);
        }else if (r == 7){
            return colors.get(7);
        }else{
            return colors.get(1);
        }
    }

}
