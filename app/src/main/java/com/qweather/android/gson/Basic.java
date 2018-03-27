package com.qweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qiujian on 3/27/18.
 */

public class Basic {
    @SerializedName("location")
    public String cityName;

    @SerializedName("cid")
    public String weatherId;
}
