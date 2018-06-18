package com.alokmishra.baadal.module.util;

import com.alokmishra.baadal.R;

public class CommonUtils {
    public static int getWeatherDrawableResource(String weatherType) {
        switch (weatherType) {
            case "Sunny":
                return R.drawable.sunny ;

            case "Mostly Sunny":
                return R.drawable.sunny_mostly ;

            case "Mostly Cloudy":
                return R.drawable.cloudy_mostly ;

            case "Partly Cloudy":
            case  "Cloudy":
                return R.drawable.cloudy_partly ;

            case "Rain":
                return R.drawable.rain ;

            case "Thunderstorms":
            case "Storms":
                return R.drawable.storm ;

            case "Scattered Showers":
                return R.drawable.scatter_shower ;

            default:
                return R.drawable.sunny ;
        }
    }
}
