package com.example.wengzc.flowers;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends Activity {

    int screenWidth;//屏幕宽度      
    int screenHeight;//屏幕高度 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorPool.init(this);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        FlowerFactory.initLimit(screenWidth, screenHeight);

        setContentView(R.layout.activity_main);
    }
}
