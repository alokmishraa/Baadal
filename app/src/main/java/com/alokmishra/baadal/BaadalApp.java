package com.alokmishra.baadal;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class BaadalApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
