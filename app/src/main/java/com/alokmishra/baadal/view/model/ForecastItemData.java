package com.alokmishra.baadal.view.model;

import com.alokmishra.baadal.model.Channel;
import com.alokmishra.baadal.model.Forecast;
import com.alokmishra.baadal.model.ForecastModel;

import java.util.ArrayList;
import java.util.List;

public class ForecastItemData {

    private List<SingleDayForecastItemData> mForecastList;
    private String mCity;

    public ForecastItemData(ForecastModel model) {
        Channel channel = model.getQuery().getResults().getChannel();
        //TODO change to String buffer
        this.mCity = channel.getLocation().getCity() + ", "
                + channel.getLocation().getRegion() + ", "
                + channel.getLocation().getCountry();

        List<Forecast> forecastList = channel.getItem().getForecast();
        this.mForecastList = new ArrayList<>();
        for(Forecast forecast : forecastList) {
            mForecastList.add(new SingleDayForecastItemData(forecast));
        }
    }

    public List<SingleDayForecastItemData> getForecastList() {
        return mForecastList;
    }

    public String getCity() {
        return mCity;
    }
}
