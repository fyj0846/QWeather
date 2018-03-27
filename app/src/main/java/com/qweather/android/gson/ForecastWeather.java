package com.qweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qiujian on 3/27/18.
 */

public class ForecastWeather {
    public String status;

    public Basic basic;

    public Update update;

//    public AQI aqi;

    public Now now;

    @SerializedName("lifestyle")
    public List<Lifestyle> lifestyleList;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
