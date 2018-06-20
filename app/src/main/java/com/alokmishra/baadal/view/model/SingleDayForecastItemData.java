package com.alokmishra.baadal.view.model;

import com.alokmishra.baadal.model.Forecast;

public class SingleDayForecastItemData {
    private String mHighTemp;
    private String mLowTemp;
    private String mText;
    private String mDay;

    public SingleDayForecastItemData(Forecast forecast) {
        mHighTemp = new StringBuilder().append(forecast.getHigh()).append("\u2103").toString();
        mLowTemp = new StringBuilder().append(forecast.getLow()).append("\u2103").toString();
        mDay = forecast.getDay();
        mText = forecast.getText();
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
}
