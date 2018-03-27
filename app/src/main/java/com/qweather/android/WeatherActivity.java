package com.qweather.android;

import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView nowTemperature;
    private TextView nowInfo;
    private LinearLayout forecastLayout;
    private TextView comfortText;
    private TextView carwashText;
    private TextView sportText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

//        https://free-api.heweather.com/s6/weather?parameters
//        609427d2fd284aef805aab5e294d9256

    }
}
