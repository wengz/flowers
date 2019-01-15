package com.example.wengzc.flowers;

import android.app.Application;

public class FlowersApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        ColorPool.init(this);
    }
}
