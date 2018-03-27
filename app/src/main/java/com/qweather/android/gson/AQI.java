package com.qweather.android.gson;

/**
 * Created by qiujian on 3/27/18.
 */

public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
