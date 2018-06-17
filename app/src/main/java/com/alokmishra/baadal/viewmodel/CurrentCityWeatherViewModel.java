package com.alokmishra.baadal.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.alokmishra.baadal.module.WeatherFetchManager;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;

public class CurrentCityWeatherViewModel extends ViewModel {
    private MutableLiveData<CurrentWeatherItemData> mCurrentLiveData;

    public void init() {
        mCurrentLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<CurrentWeatherItemData> getCurrentLiveData() {
        return mCurrentLiveData;
    }

    public void start(String city) {
        new WeatherFetchManager().getCurrentWeather(city, mCurrentLiveData);
    }
}
