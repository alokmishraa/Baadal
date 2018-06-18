
package com.alokmishra.baadal.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("condition")
    @Expose
    private Condition condition;
    @SerializedName("forecast")
    @Expose
    private List<Forecast> forecast = new ArrayList<Forecast>();

    public Condition getCondition() {
        return condition;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }
}
