package com.qweather.android;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.qweather.android.gson.ForecastDay;
import com.qweather.android.gson.ForecastHour;
import com.qweather.android.gson.Lifestyle;
import com.qweather.android.gson.Weather;
import com.qweather.android.util.HttpUtil;
import com.qweather.android.util.Utility;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";

    private ScrollView weatherLayout;
    private TextView titleCityText;
    private TextView titleUpdateTimeText;
    private TextView nowTemperatureText;
    private TextView nowInfoText;
    private LinearLayout forecastHourLayout;
    private LinearLayout forecastDayLayout;
    private TextView lsComfortText;
    private TextView lsCarWashText;
    private TextView lsDressText;
    private TextView lsFluText;
    private TextView lsSportText;
    private TextView lsTravelText;
    private TextView lsUVText;
    private TextView lsAirText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

//        https://free-api.heweather.com/s6/weather?parameters
//        609427d2fd284aef805aab5e294d9256

        // 初始化控件
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCityText = (TextView) findViewById(R.id.title_city);
        titleUpdateTimeText = (TextView) findViewById(R.id.title_update_time);
        nowTemperatureText = (TextView) findViewById(R.id.now_temperature_text);
        nowInfoText = (TextView) findViewById(R.id.now_info_text);
        forecastHourLayout = (LinearLayout) findViewById(R.id.forecast_h_layout);
        forecastDayLayout = (LinearLayout) findViewById(R.id.forecast_d_layout);
        lsComfortText = (TextView) findViewById(R.id.lifestyle_comfort_text);
        lsCarWashText = (TextView) findViewById(R.id.lifestyle_car_wash_text);
        lsDressText = (TextView) findViewById(R.id.lifestyle_drsg_text);
        lsFluText = (TextView) findViewById(R.id.lifestyle_flu_text);
        lsSportText = (TextView) findViewById(R.id.lifestyle_sport_text);
        lsTravelText = (TextView) findViewById(R.id.lifestyle_trav_text);
//        lsUVText = (TextView) findViewById(R.id.lifestyle_uv_text);
        lsAirText = (TextView) findViewById(R.id.lifestyle_air_text);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if(weatherString != null) {
            // 缓存天气信息
            Log.d(TAG, "onCreate: " + weatherString);
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
            // 无天气缓存信息
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
    }

    public void requestWeather(final String weatherId) {
        String url =  "https://free-api.heweather.com/s6/weather?location=" +
                weatherId + "&key=609427d2fd284aef805aab5e294d9256";
        Log.d(TAG, "requestWeather: " + url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.update.updateTime.split(" ")[1];
        String temperature = weather.now.temperature;
        String now_info = weather.now.info;
        titleCityText.setText(cityName);
        titleUpdateTimeText.setText(updateTime);
        nowTemperatureText.setText(temperature + "℃");
        nowInfoText.setText(now_info);
//        forecastHourLayout.removeAllViews();
//        for(ForecastHour fhour : weather.forecastHourList) {
//            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item_h, forecastHourLayout, false);
//            TextView timeText = view.findViewById(R.id.forecast_h_time_text);
//            TextView infoText= view.findViewById(R.id.forecast_h_info_text);
//            TextView forecast_d_tmp = view.findViewById(R.id.forecast_h_temperature_text);
//            timeText.setText(fhour.time);
//            infoText.setText(fhour.info);
//            forecast_d_tmp.setText(fhour.temperature);
//            forecastHourLayout.addView(view);
//        }
        forecastDayLayout.removeAllViews();
        for(ForecastDay fDay : weather.forecastDayList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item_d, forecastDayLayout, false);
            TextView dateText = view.findViewById(R.id.forecast_d_date_text);
            TextView infoText = view.findViewById(R.id.forecast_d_info_text);
            TextView maxTmp = view.findViewById(R.id.forecast_d_max_text);
            TextView minTmp = view.findViewById(R.id.forecast_d_min_text);

            dateText.setText(fDay.date);
            infoText.setText(fDay.info);
            maxTmp.setText(fDay.max);
            minTmp.setText(fDay.min);
            forecastDayLayout.addView(view);
        }

        for(Lifestyle ls : weather.lifestyleList) {
            if("comf".equals(ls.type)) {
                lsComfortText.setText("舒适度：" + ls.brief + " " + ls.info);
            } else if("cw".equals(ls.type)){
                lsCarWashText.setText("洗车指数：" + ls.brief + " " + ls.info);
            } else if("drsg".equals(ls.type)){
                lsDressText.setText("穿衣指数：" + ls.brief + " " + ls.info);
            } else if("flu".equals(ls.type)){
                lsFluText.setText("流感指数：" + ls.brief + " " + ls.info);
            } else if("sport".equals(ls.type)){
                lsSportText.setText("运动指数：" + ls.brief + " " + ls.info);
            } else if("trav".equals(ls.type)){
                lsTravelText.setText("旅游指数：" + ls.brief + " " + ls.info);
            }
//            else if("UV".equals(ls.type)){
//                lsUVText.setText("紫外线强度：" + ls.brief + " " + ls.info);
//            }
            else if("air".equals(ls.type)){
                lsAirText.setText("空气质量：" + ls.brief + " " + ls.info);
            }
        }
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
