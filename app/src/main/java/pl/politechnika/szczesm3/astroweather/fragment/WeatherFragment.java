package pl.politechnika.szczesm3.astroweather.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import pl.politechnika.szczesm3.astroweather.FavoriteActivity;
import pl.politechnika.szczesm3.astroweather.R;
import pl.politechnika.szczesm3.astroweather.config.AppConfig;
import pl.politechnika.szczesm3.astroweather.data.Channel;
import pl.politechnika.szczesm3.astroweather.data.Place;
import pl.politechnika.szczesm3.astroweather.manager.FileManager;
import pl.politechnika.szczesm3.astroweather.service.Callback;
import pl.politechnika.szczesm3.astroweather.service.YahooWeatherService;

public class WeatherFragment extends Fragment implements Callback {

    FileManager fm;
    YahooWeatherService service;
    ImageView weather;
    TextView cityName, latitude, longitude, temperature, pressure,
             windSpeed, windDirection,  humidity, visibility;
    FloatingActionButton fab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View weatherView = inflater.inflate(R.layout.fragment_weather, container, false);

        service = new YahooWeatherService(this);
        fm = new FileManager(getContext());

        cityName = weatherView.findViewById(R.id.cityName);
        latitude = weatherView.findViewById(R.id.latitude);
        longitude = weatherView.findViewById(R.id.longitude);
        temperature = weatherView.findViewById(R.id.temperature);
        pressure = weatherView.findViewById(R.id.pressure);
        windSpeed = weatherView.findViewById(R.id.windSpeed);
        windDirection = weatherView.findViewById(R.id.windDirection);
        humidity = weatherView.findViewById(R.id.humidity);
        visibility = weatherView.findViewById(R.id.visibility);
        fab = weatherView.findViewById(R.id.floatingActionButton);
        weather = weatherView.findViewById(R.id.imageView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    service.getForecast(AppConfig.getInstance().getWoeid(), AppConfig.getInstance().getUnits());
                } else {
                    Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_LONG).show();
                }
            }
        });

        loadData();

        return weatherView;
    }

    private void loadData() {
        Channel channel = new Channel();
        final String fileName = AppConfig.getInstance().getWoeid() + ".json";
        if (fm.isForecastUpToDate(fileName)) {
            channel.crawl(fm.readForecastFromFile(fileName));
            setData(channel);
        } else if (isInternetAvailable()) {
            service.getForecast(AppConfig.getInstance().getWoeid(), AppConfig.getInstance().getUnits());
        } else {
            Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setData(Channel channel) {
        cityName.setText(channel.location.city + ", " + channel.location.country);
        latitude.setText(String.valueOf(channel.item.lat));
        longitude.setText(String.valueOf(channel.item.lon));
        temperature.setText(String.valueOf(channel.item.condition.temp) + " " + channel.unit.temperature);
        pressure.setText(String.valueOf(channel.atmosphere.pressure) + " " + channel.unit.pressure);
        windSpeed.setText(String.valueOf(channel.wind.speed) + " " + channel.unit.speed);
        windDirection.setText(String.valueOf(channel.wind.direction) + " °");
        humidity.setText(String.valueOf(channel.atmosphere.humidity) + " %");
        visibility.setText(String.valueOf(channel.atmosphere.visibility) + " " + channel.unit.distance);
        int imageId = getResources().getIdentifier("drawable/big_icon_" + channel.item.condition.code, null, Objects.requireNonNull(getActivity()).getPackageName());
        weather.setImageResource(imageId);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void callbackSuccess(JSONObject json) {
        Channel channel = new Channel();
        channel.crawl(json);
        fm.saveForecastToFile(AppConfig.getInstance().getWoeid() + ".json", json);
        setData(channel);
    }


    @Override
    public void callbackFailure(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
