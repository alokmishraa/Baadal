package com.alokmishra.baadal.module;

import android.util.Log;

import com.alokmishra.baadal.network.ApiClient;
import com.alokmishra.baadal.network.ApiInterface;

import java.io.IOException;

public class WeatherFetchManager {

    private static final String TAG = WeatherFetchManager.class.getSimpleName();
    private ApiInterface mApiInterface;
    private static WeatherFetchManager sInstance;

    private WeatherFetchManager() {

    }

    public static WeatherFetchManager getInstance() {
        if(sInstance == null) {
            synchronized (WeatherFetchManager.class) {
                if(sInstance == null) {
                    sInstance = new WeatherFetchManager();
                }
            }
        }
        return sInstance;
    }

    private ApiInterface getApiInterface() {
        if(mApiInterface == null) {
            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        }
        return mApiInterface;
    }

    public void hitApiCall() {
        try {
            getApiInterface().getForeCast(ApiInterface.FORECAST_URL).execute();
        } catch (IOException e) {
            Log.d(TAG, "Exception e "+e);
            e.printStackTrace();
        }
    }
}
