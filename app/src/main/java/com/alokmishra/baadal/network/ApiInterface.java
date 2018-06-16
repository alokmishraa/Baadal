package com.alokmishra.baadal.network;

import com.alokmishra.baadal.model.ForecastModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    public static final String FORECAST_URL =
            "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"nome, ak\")";

    /*@GET("/weather")
    Call<Weather> getWeatherList(@Query("q") String city,@Query("key") String apiKey);

    @GET("/weather")
    Call<Weather> getLongLatWeather(@Query("lon") String lon,@Query("lat") String lat,@Query("key") String apiKey);

    @GET("/forecast/daily")
    Call<WeatherForecast> getForecastDaily(@Query("q") String city, @Query("key") String apiKey);*/


    @GET()
    Call<ForecastModel> getForeCast(@Query("q") String forcast);
}