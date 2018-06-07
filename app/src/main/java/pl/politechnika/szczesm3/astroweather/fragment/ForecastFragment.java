package pl.politechnika.szczesm3.astroweather.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.politechnika.szczesm3.astroweather.R;
import pl.politechnika.szczesm3.astroweather.config.AppConfig;
import pl.politechnika.szczesm3.astroweather.data.Channel;
import pl.politechnika.szczesm3.astroweather.data.Forecast;
import pl.politechnika.szczesm3.astroweather.manager.FileManager;
import pl.politechnika.szczesm3.astroweather.service.Callback;
import pl.politechnika.szczesm3.astroweather.service.YahooWeatherService;

public class ForecastFragment extends Fragment implements Callback{

    FileManager fm;
    YahooWeatherService service;
    FloatingActionButton fab;
    Channel channel;
    ForecastRow[] oldRows = new ForecastRow[AppConfig.DAYS_COUNT];
    View forecastView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.forecastView = inflater.inflate(R.layout.fragment_forecast, container, false);

        service = new YahooWeatherService(this);
        fm = new FileManager(getContext());

        loadData();

        fab = this.forecastView.findViewById(R.id.floatingActionButton);
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

        initFragments(this.forecastView);

        return this.forecastView;
    }

    private void initFragments(View view) {
        FrameLayout[] frames = {
                view.findViewById(R.id.day1),
                view.findViewById(R.id.day2),
                view.findViewById(R.id.day3),
                view.findViewById(R.id.day4)
        };

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

        for (int i = 0; i < AppConfig.DAYS_COUNT; i++) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("dayForecast", channel.item.forecast.dayForecasts.get(i));

            ForecastRow forecastRow = new ForecastRow();
            forecastRow.setArguments(bundle);
            if (oldRows[i] != null) {
                fragTransaction.detach(oldRows[i]);
                oldRows[i] = forecastRow;
            } else {
                oldRows[i] = forecastRow;
            }
            fragTransaction.add(frames[i].getId(), forecastRow, "fragment " + i);
        }
        fragTransaction.commit();
    }

    private void loadData() {
        this.channel = new Channel();
        final String fileName = AppConfig.getInstance().getWoeid() + AppConfig.getInstance().getUnits() + ".json";
        if (fm.isForecastUpToDate(fileName)) {
            this.channel.crawl(fm.readForecastFromFile(fileName));
            initFragments(this.forecastView);
        } else if (isInternetAvailable()) {
            service.getForecast(AppConfig.getInstance().getWoeid(), AppConfig.getInstance().getUnits());
        } else {
            Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void callbackSuccess(JSONObject json) {
        channel = new Channel();
        channel.crawl(json);
        fm.saveForecastToFile(AppConfig.getInstance().getWoeid() + AppConfig.getInstance().getUnits() + ".json", json);
        initFragments(this.forecastView);
    }

    @Override
    public void callbackFailure(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
