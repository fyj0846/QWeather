package com.qweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qiujian on 3/27/18.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond_txt")
    public String info;
    @SerializedName("win_dir")
    public String windDirect;
    @SerializedName("win_sc")
    public String windLevel;
    @SerializedName("win_spd")
    public String windSpeed;
}
