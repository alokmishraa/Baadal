package com.alokmishra.baadal.view.model;

import com.alokmishra.baadal.model.Channel;
import com.alokmishra.baadal.model.Forecast;
import com.alokmishra.baadal.model.ForecastModel;

public class CurrentWeatherItemData {
    private String mCurrentTemp;
    private String mHighTemp;
    private String mLowTemp;
    private String mText;
    private String mDay;
    private String mWindSpeed;
    private String mSunRise;
    private String mSunSet;
    private String mCity;

    public CurrentWeatherItemData (ForecastModel forecast) {
        Channel channel = forecast.getQuery().getResults().getChannel();

        this.mCity = new StringBuilder(channel.getLocation().getCity()).append( ", ").append(
                      channel.getLocation().getRegion()).append(", ").append(
                      channel.getLocation().getCountry()).toString();

        this.mCurrentTemp = new StringBuilder(channel.getItem().getCondition().getTemp()).append(" \u2103").toString();
        this.mHighTemp = new StringBuilder(channel.getItem().getForecast().get(0).getHigh()).append(" \u2103").toString();
        this.mLowTemp = new StringBuilder(channel.getItem().getForecast().get(0).getLow()).append(" \u2103").toString();
        this.mSunRise = channel.getAstronomy().getSunrise();
        this.mSunSet = channel.getAstronomy().getSunset();
        this.mWindSpeed = channel.getWind().getSpeed();
        this.mText = channel.getItem().getCondition().getText();
        this.mDay = channel.getItem().getForecast().get(0).getDay();
    }

    public String getCurrentTemp() {
        return mCurrentTemp;
    }

    public String getHighTemp() {
        return mHighTemp;
    }

    public String getLowTemp() {
        return mLowTemp;
    }

    public String getText() {
        return mText;
    }

    public String getDay() {
        return mDay;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public String getSunRise() {
        return mSunRise;
    }

    public String getSunSet() {
        return mSunSet;
    }

    public String getCity() {
        return mCity;
    }
}
