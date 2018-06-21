package com.alokmishra.baadal.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.alokmishra.baadal.module.WeatherFetchManager;
import com.alokmishra.baadal.module.util.OnNetworkFailureListener;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;
import com.alokmishra.baadal.view.model.ForecastItemData;

public class ForecastViewModel extends ViewModel{

    private MutableLiveData<CurrentWeatherItemData> mCurrentLiveData;
    private MutableLiveData<ForecastItemData> mForecastLiveData;


    public void init() {
        mCurrentLiveData = new MutableLiveData<>();
        mForecastLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<CurrentWeatherItemData> getCurrentLiveData() {
        return mCurrentLiveData;
    }


    public MutableLiveData<ForecastItemData> getForecastLiveData() {
        return mForecastLiveData;
    }

    public void start(String city, OnNetworkFailureListener listener) {
        new WeatherFetchManager().getForecastForCity(city, mForecastLiveData, mCurrentLiveData, listener);
    }
}
