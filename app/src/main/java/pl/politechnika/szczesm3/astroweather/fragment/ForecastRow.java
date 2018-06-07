package pl.politechnika.szczesm3.astroweather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import pl.politechnika.szczesm3.astroweather.R;
import pl.politechnika.szczesm3.astroweather.data.DayForecast;

public class ForecastRow extends Fragment {

    DayForecast dayForecast;
    TextView date, temp, forecast;
    ImageView weather;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View forecastView = inflater.inflate(R.layout.forecast_row, container, false);

        if (savedInstanceState != null) {
            dayForecast = (DayForecast) savedInstanceState.getSerializable("dayForecast");
        }

        date = forecastView.findViewById(R.id.date);
        temp = forecastView.findViewById(R.id.temp);
        forecast = forecastView.findViewById(R.id.forecast);
        weather = forecastView.findViewById(R.id.imageView);

        loadData();

        return forecastView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        Log.d("FORECAST: ", dayForecast.toString());
        date.setText(dayForecast.day + " " + dayForecast.date);
        temp.setText("↑ "+ dayForecast.high + "  |  ↓ " + dayForecast.low);
        forecast.setText(dayForecast.text);
        int imageId = getResources().getIdentifier("drawable/big_icon_" + dayForecast.code, null, Objects.requireNonNull(getActivity()).getPackageName());
        weather.setImageResource(imageId);
    }
}
