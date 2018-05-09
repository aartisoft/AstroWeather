package pl.politechnika.szczesm3.astroweather.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import pl.politechnika.szczesm3.astroweather.R;
import pl.politechnika.szczesm3.astroweather.config.AppConfig;

public class SunFragment extends Fragment {

    private double LAT, LON;
    private Integer FREQ;
    private TextView sunriseTime, sunriseAzymut, sunsetTime, sunsetAzymut, dusk, dawn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View sunView = inflater.inflate(R.layout.fragment_sun, container, false);
        retrieveTextViews(sunView);
        fetchData();
        init();
        return sunView;
    }


    @Override
    public void onResume() {
        super.onResume();
        fetchData();
        init();
    }

    private void retrieveTextViews(View v) {
        sunriseTime = v.findViewById(R.id.sunriseTime);
        sunriseAzymut = v.findViewById(R.id.sunriseAzymut);
        sunsetTime = v.findViewById(R.id.sunsetTime);
        sunsetAzymut = v.findViewById(R.id.sunsetAzymut);
        dusk = v.findViewById(R.id.dusk);
        dawn = v.findViewById(R.id.dawn);
    }

    private void fetchData() {
        LAT = AppConfig.getInstance().getLatitude();
        LON = AppConfig.getInstance().getLongtitude();
        FREQ = AppConfig.getInstance().getRefreshInterval();
    }

    private void init() {
        AstroCalculator.Location loc = new AstroCalculator.Location(LAT, LON);
        AstroDateTime date = new AstroDateTime();
        final AstroCalculator calc = new AstroCalculator(date, loc);
        setData(calc);
    }

    private void setData(AstroCalculator calc) {
        sunriseTime.setText(calc.getSunInfo().getSunrise().toString());
        sunriseAzymut.setText(String.valueOf(calc.getSunInfo().getAzimuthRise()));
        sunsetTime.setText(calc.getSunInfo().getSunset().toString());
        sunsetAzymut.setText(String.valueOf(calc.getSunInfo().getAzimuthSet()));
        dusk.setText(calc.getSunInfo().getTwilightMorning().toString());
        dawn.setText(calc.getSunInfo().getTwilightEvening().toString());
    }
}
