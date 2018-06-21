package com.alokmishra.baadal.module;

import android.arch.lifecycle.MutableLiveData;

import com.alokmishra.baadal.model.ForecastModel;
import com.alokmishra.baadal.module.network.ApiClient;
import com.alokmishra.baadal.module.network.ApiInterface;
import com.alokmishra.baadal.module.util.ConversionUtils;
import com.alokmishra.baadal.module.util.OnNetworkFailureListener;
import com.alokmishra.baadal.module.util.UrlUtils;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;
import com.alokmishra.baadal.view.model.ForecastItemData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFetchManager {

    private static final String TAG = WeatherFetchManager.class.getSimpleName();
    private static ApiInterface sApiInterface;

    public void getForecastForCity(final String city, final MutableLiveData<ForecastItemData> forecastLiveData, final MutableLiveData<CurrentWeatherItemData> currentLiveData, final OnNetworkFailureListener listener) {
        getApiInterface().getForeCast(UrlUtils.getForecastUrlForCity(city)).enqueue(new Callback<ForecastModel>() {
            @Override
            public void onResponse(Call<ForecastModel> call, Response<ForecastModel> response) {
                forecastLiveData.postValue(ConversionUtils.getForecastItemDataFromForecastData(response.body()));
                currentLiveData.postValue(ConversionUtils.getCurretnWeatherDataFromForecastData(response.body()));
            }

            @Override
            public void onFailure(Call<ForecastModel> call, Throwable t) {
                listener.onFailure(city);
            }
        });
    }

    private ApiInterface getApiInterface() {
        if (sApiInterface == null) {
            synchronized (WeatherFetchManager.class) {
                if (sApiInterface == null) {
                    sApiInterface = ApiClient.getClient().create(ApiInterface.class);
                }
            }
        }
        return sApiInterface;
    }
}
