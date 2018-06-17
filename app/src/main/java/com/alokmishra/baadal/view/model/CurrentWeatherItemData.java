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

        //TODO change to String buffer
        this.mCity = channel.getLocation().getCity() + ", "
                     + channel.getLocation().getRegion() + ", "
                     + channel.getLocation().getCountry();
        //TODO add temprature degree sign
        this.mCurrentTemp = channel.getItem().getCondition().getTemp();
        this.mHighTemp = channel.getItem().getForecast().get(0).getHigh();
        this.mLowTemp = channel.getItem().getForecast().get(0).getLow();
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
