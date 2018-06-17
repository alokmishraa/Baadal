package com.alokmishra.baadal.module;

import com.alokmishra.baadal.model.ForecastModel;
import com.alokmishra.baadal.module.network.ApiClient;
import com.alokmishra.baadal.module.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            getApiInterface().getForeCast(ApiInterface.FORECAST_URL).enqueue(new Callback<ForecastModel>() {
                @Override
                public void onResponse(Call<ForecastModel> call, Response<ForecastModel> response) {

                }

                @Override
                public void onFailure(Call<ForecastModel> call, Throwable t) {

                }
            });
    }
}
