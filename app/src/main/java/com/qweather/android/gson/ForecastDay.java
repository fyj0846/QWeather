package com.qweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qiujian on 3/27/18.
 */

public class ForecastDay {
    public String date;

    @SerializedName("tmp_max")
    public String max;

    @SerializedName("tmp_min")
    public String min;

    @SerializedName("cond_txt_d")
    public String info;
}
