package com.alokmishra.baadal.module.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alokmishra.baadal.BaadalApp;
import com.alokmishra.baadal.R;

public class CommonUtils {

    public static boolean haveNetworkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) BaadalApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

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

    public static void saveLastCity(String city) {
        getSharedPrefrences().edit().putString(Constants.KEY_CITY, city).commit();
    }

    public static String getSavedCity() {
        return getSharedPrefrences().getString(Constants.KEY_CITY, "");
    }

    private static SharedPreferences getSharedPrefrences () {
        SharedPreferences sharedPreferences =  BaadalApp.getInstance().getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

}
