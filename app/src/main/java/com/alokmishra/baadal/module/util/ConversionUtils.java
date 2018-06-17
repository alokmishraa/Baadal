package com.alokmishra.baadal.module.util;

import com.alokmishra.baadal.model.ForecastModel;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;
import com.alokmishra.baadal.view.model.ForecastItemData;

public class ConversionUtils {
    public static CurrentWeatherItemData getCurretnWeatherDataFromForecastData(ForecastModel forecastModel) {
        CurrentWeatherItemData itemData = new CurrentWeatherItemData(forecastModel);
        return itemData;
    }

    public static ForecastItemData getForecastItemDataFromForecastData(ForecastModel forecastModel) {
        ForecastItemData forecastItemData = new ForecastItemData(forecastModel);
        return forecastItemData;
    }
}
