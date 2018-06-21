package com.alokmishra.baadal;

import android.app.Application;

public class BaadalApp extends Application {

    private static BaadalApp sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static BaadalApp getInstance() {
        return sInstance;
    }
}
