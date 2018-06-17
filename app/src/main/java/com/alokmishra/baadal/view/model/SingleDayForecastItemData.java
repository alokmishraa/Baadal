package com.alokmishra.baadal.view.model;

import com.alokmishra.baadal.model.Forecast;

public class SingleDayForecastItemData {
    private String mHighTemp;
    private String mLowTemp;
    private String mText;
    private String mDay;

    public SingleDayForecastItemData(Forecast forecast) {
        mHighTemp = forecast.getHigh();
        mLowTemp = forecast.getLow();
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
