package com.qweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qiujian on 3/27/18.
 */

public class Lifestyle {
    public String type;

    @SerializedName("brf")
    public String brief;

    @SerializedName("txt")
    public String info;
}
