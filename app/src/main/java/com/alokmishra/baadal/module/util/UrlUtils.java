package com.alokmishra.baadal.module.util;

public class UrlUtils {
    public static String getForecastUrlForCity(String city) {
        StringBuilder builder = new StringBuilder()
                .append("select * from weather.forecast where u=\"C\" and woeid in")
                .append("(")
                .append("select woeid from geo.places(1) where text=")
                .append("\"")
                .append(city)
                .append("\"")
                .append(")");
        return builder.toString();
    }
}
