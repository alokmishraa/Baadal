package com.alokmishra.baadal.view.model;

import com.alokmishra.baadal.model.Channel;
import com.alokmishra.baadal.model.Forecast;
import com.alokmishra.baadal.model.ForecastModel;

import java.util.ArrayList;
import java.util.List;

public class ForecastItemData {

    private List<SingleDayForecastItemData> mForecastList;

    public ForecastItemData(ForecastModel model) {
        Channel channel = model.getQuery().getResults().getChannel();

        List<Forecast> forecastList = channel.getItem().getForecast();
        this.mForecastList = new ArrayList<>();
        for(Forecast forecast : forecastList) {
            mForecastList.add(new SingleDayForecastItemData(forecast));
        }
    }
    public List<SingleDayForecastItemData> getForecastList() {
        return mForecastList;
    }

}
