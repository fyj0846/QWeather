package com.qweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qiujian on 3/28/18.
 */

public class ForecastHour {
    public String time;

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond_txt")
    public String info;
}
