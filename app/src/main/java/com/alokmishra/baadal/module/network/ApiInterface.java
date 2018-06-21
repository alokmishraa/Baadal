package com.alokmishra.baadal.module.network;

import com.alokmishra.baadal.model.ForecastModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v1/public/yql?format=json")
    Call<ForecastModel> getForeCast(@Query("q") String forcastQuesy);

}