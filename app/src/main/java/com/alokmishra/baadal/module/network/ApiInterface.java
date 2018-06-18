package com.alokmishra.baadal.module.network;

import com.alokmishra.baadal.model.ForecastModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    /*@GET("/weather")
    Call<Weather> getWeatherList(@Query("q") String city,@Query("key") String apiKey);

    @GET("/weather")
    Call<Weather> getLongLatWeather(@Query("lon") String lon,@Query("lat") String lat,@Query("key") String apiKey);

    @GET("/forecast/daily")
    Call<WeatherForecast> getForecastDaily(@Query("q") String city, @Query("key") String apiKey);*/


    @GET("v1/public/yql?format=json")
    Call<ForecastModel> getForeCast(@Query("q") String forcastQuesy);

}