package com.alokmishra.baadal.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.alokmishra.baadal.module.WeatherFetchManager;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;
import com.alokmishra.baadal.view.model.ForecastItemData;

public class ForecastViewModel {

    private MutableLiveData<ForecastItemData> mCurrentLiveData;

    public void init() {
        mCurrentLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ForecastItemData> getCurrentLiveData() {
        return mCurrentLiveData;
    }

    public void start(String city) {
        new WeatherFetchManager().getForecastForCity(city, mCurrentLiveData);
    }
}
